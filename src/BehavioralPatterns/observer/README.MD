# 观察者模式
观察者模式定义了一种一对多的依赖关系，一个对象的状态改变，其他所有依赖者都会接收相应的通知。

所以，
- 何时使用: 一个对象的状态改变，其他所有依赖对象都要知道
- 意图: 定义一种一对多的依赖关系，一个对象状态改变，所有其他对象都要得到通知
- 主要解决: 在降低耦合的基础上，实现一对多的协作

观察者模式中，存在四种角色：
- 主题 (Subject): 被观察者，它要有能通知的状态，要维护一个观察者列表。
- 观望者(Observer): 观察者，接收主题的通知。需要有一个更新方法，接收到主题状态改变后调用。
- 具体主题(Concrete Subject): 主题的实现类。
- 具体观察者(Concrete Observer): 观察者的实现类，实现更新方法。

是不是觉得，四种角色突然被引入，十分抽象？别急，看看下面的故事。

## 1. 情景模拟
现在宝可梦研究所业务如日中天，人人都想开个研究所狠狠地赚一笔。
但是想要开宝可梦研究所，那肯定得先把宝可梦研究透啊，怎么研究呢？先抓回来！
所以新开的小木博士研究所就打算把关都地区和城都地区的宝可梦都给抓来再说。
小木博士决定，雇点训练家来抓宝可梦，于是大量的训练家都来兼职。
作为亲属的小智和小茂也来兼职帮个忙，小智负责关都地区，小茂负责成都地区。

为了控制成本，每种宝可梦抓一只就够了，所以，每当一个训练家抓到一个精灵，就要通知一下其他同行：
某个地区已经抓住了多少个精灵啦，总共还剩多少个，以免大家抓到重复。
(当然，因为小智和小茂是家属，所以开点小灶，只通知了他俩。)
于是可以引入了观察者模式的各个对象：

- 主题: 研究所的业务
- 观察者: 训练家
- 具体主题: 抓宝可梦的任务
- 具体观察者: 负责不同地区的训练家

## 2. 代码
先随便定义一个主题的接口
```java
public interface SubjectLab {
}
```

定义观察者的接口，即训练家，其中，观察者依赖他要观察的Subject类
```java
/**
 * 训练家
 */
public abstract class ObserverTrainer {
    protected SubjectPokedex subject;
    public abstract void update();
}
```

小智和小茂分别负责各自地区
```java
public class Satoshi extends ObserverTrainer{

    // 该对象观察这个Subject
    // 同时Subject也绑定这个对象，会发送通知
    public Satoshi(SubjectPokedex pokedex) {
        this.subject = pokedex;
        this.subject.employ(this);
    }

    @Override
    public void update() {
        System.out.println("I am Satoshi! I got the message:");
        this.subject.getDexState("Kanto");
    }
}

public class Shigeru extends ObserverTrainer{
    public Shigeru(SubjectPokedex subject) {
        this.subject = subject;
        this.subject.employ(this);
    }

    @Override
    public void update() {
        System.out.println("I am Shigeru! I got the message:");
        this.subject.getDexState("Johto");
    }
}
```

最后是这次抓宝可梦任务的实现类
```java
public class SubjectPokedex implements SubjectLab{
    private List<ObserverTrainer> trainers = new ArrayList<>();
    private HashMap<String, Integer> pokedex = new HashMap<>();
    private Integer num;

    /**
     * 初始化，假设各个地区已经发现了很多宝可梦
     * 还有value个宝可梦没有被抓到研究所研究
     * num统计总数
     */
    public SubjectPokedex() {
        pokedex.put("Kanto", 155);
        pokedex.put("Johto", 155);
        num = 310;
    }

    /**
     * 打印某地区剩下的宝可梦
     * @param region 地区
     */
    public void getDexState(String region){
        System.out.println("There are still " + pokedex.getOrDefault(region, 0) + " Pokemons in " +
                region + " that haven't been captured.");
        System.out.println("There are a total of " + num +" Pokemon that have not been captured yet.\n");
    }

    /**
     * 抓获了num个宝可梦
     */
    public void catchPokemon(String region, Integer num){
        this.pokedex.put(region, pokedex.get(region) - num);
        this.num -= num;
        notifyAllTrainers();
    }

    /**
     * 任命愿意来帮助捕捉宝可梦的训练家们
     * @param trainer 训练家
     */
    public void employ(ObserverTrainer trainer){
        trainers.add(trainer);
    }

    /**
     * 通知所有参与的训练家
     */
    public void notifyAllTrainers(){
        for (ObserverTrainer trainer : trainers) {
            trainer.update();
        }
    }
}

```

**测试类**
```java
public class ObserverDemo {
    public static void main(String[] args) {
        // Subject对象
        SubjectPokedex subjectPokedex = new SubjectPokedex();
        
        // 两个观察者，接收Subject的通知
        new Satoshi(subjectPokedex);
        new Shigeru(subjectPokedex);

        // 通知：关都地区抓住了30只
        subjectPokedex.catchPokemon("Kanto", 30);
        
        // 通知：城都地区抓住了90只
        subjectPokedex.catchPokemon("Johto", 90);
    }
}
```

```
I am Satoshi! I got the message:
There are still 125 Pokemons in Kanto that haven't been captured.
There are a total of 280 Pokemon that have not been captured yet.

I am Shigeru! I got the message:
There are still 155 Pokemons in Johto that haven't been captured.
There are a total of 280 Pokemon that have not been captured yet.

I am Satoshi! I got the message:
There are still 125 Pokemons in Kanto that haven't been captured.
There are a total of 190 Pokemon that have not been captured yet.

I am Shigeru! I got the message:
There are still 65 Pokemons in Johto that haven't been captured.
There are a total of 190 Pokemon that have not been captured yet.
```

## 3.应用
笔者水平有限，暂时不知道Java哪个常用API用到了观察者模式。
不过可以根据这个思想大概猜测，一种注册端口，再监听的做法和这个差不多。

如果你的微博关注了某人，TA发微博时会推送给你，关注这个行为就相当于是你开始观察这个人(Subject).
或者Steam心愿单添加了某款游戏，这游戏降价时也会推送给你，推送给每个观察者(添加愿望单的玩家).

## 4.和发布-订阅模式的讨论
有了解过消息队列的聪明的训练家就会感觉：这和发布-订阅模式很像啊！
在查阅资料的过程中，发现有些博主会把发布-订阅模式与观察者模式划等号，其实细品的话还是有一些区别。

我认为最大的区别在于，发布-订阅模式的主要目的是将发布者与订阅者解耦，发布者将消息发送给中间代理，然后代理分发消息给订阅者，让订阅者和发布者之间无需互相关注。
而观察者模式，强调的是观察者和被观察者之间的联系，被观察者(Subject)甚至会负责维护一个观察者的抽象类的列表，他们之间的联系是紧密的。

第二个区别，发布-订阅模式的消息传递是通过中间代理，而观察者模式是观察者与被观察者之间直接通信。

所以发布-订阅模式是不能跟观察者模式划等号的，观察者模式是一种更为简单的设计理念，而发布-订阅模式适用于更复杂的业务场景。

