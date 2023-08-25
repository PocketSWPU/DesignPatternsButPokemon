# 责任链模式
责任链模式为请求创建一个接受者对象的链。实现请求者与请求接受者的解耦，即请求者无需关心是谁接受本次请求。

责任链模式中，存在三种角色：
- Handler: 定义一个接口，接口包含处理请求的方法和设置后续连接的方法
- Concrete handler: 具体的实现类
- Client: 负责创建责任链，并给责任链的头节点提交请求，不关心后续谁来具体负责

所以，责任链模式的核心就是创建一个单向的链式调用结构，并定义每一级别的实现规则

## 1 情景模拟
众所周知，宝可梦训练家里一个区域最nb的头衔就是冠军，想要成为冠军，你先得打败8位道馆馆主(Gym Leader), 
然后前往冠军之路，打败4位四天王(Elite Four), 最后才能跟冠军交手。
所以，我们可以模拟出一条责任链: 道馆馆主 -> 四天王 -> 冠军
他们的责任就是：打败挑战者(battle)。
当馆主被打败，他们知道这b挑战者是个硬茬，把责任通过链条转给下一位负责人四天王，同理，四天王搞不定的，就交给冠军修理。

所以我们可以抽象出责任链模式中各个角色：
- Handler: 这是一个接口，包含了馆主/四天王/冠军共有的能力，比如接受训练赛挑战，设置下一个负责人
- Concrete Handler: 实现类，本例中，有馆主/四天王/冠军三个实现类，其中接受挑战(battle)方法中，实现各自逻辑
- Client: 客户端，设置链条，模拟情景

## 2 代码实现

### 2.1 Handler
```java
public interface Enemy {
    /**
     * 接受挑战
     * @param challengerPower 挑战者的能力值
     */
    void battle(int challengerPower);

    /**
     * 设置下一个负责人
     * @param next
     */
    void setNextLevel(Enemy next);
}
```

### 2.2 Concrete Handler
```java
/**
 * 馆主
 */
public class GymLeader implements Enemy{
    private Enemy nextLevel;
    int power = 4;
    @Override
    public void battle(int challengerPower) {
        if (challengerPower >= power){
            System.out.println("Beat gym leaders!!");
            if (nextLevel != null){
                nextLevel.battle(challengerPower);
            }
        } else{
            System.out.println("Lost to gym leaders...");
        }
    }

    @Override
    public void setNextLevel(Enemy next) {
        this.nextLevel = next;
    }
}

/**
 * 四天王
 */
public class EliteFour implements Enemy{
    private Enemy nextLevel;
    int power = 9;
    @Override
    public void battle(int challengerPower) {
        if (challengerPower >= power){
            System.out.println("Beat the Elite Four!!");
            if (nextLevel != null){
                nextLevel.battle(challengerPower);
            }
        }else {
            System.out.println("Lost to the Elite Four");
        }
    }

    @Override
    public void setNextLevel(Enemy next) {
        this.nextLevel = next;
    }
}

/**
 * 冠军
 */
public class Champion implements Enemy{
    private Enemy nextLevel;
    int power = 15;
    @Override
    public void battle(int challengerPower) {
        if (challengerPower >= power){
            System.out.println("Beat champion!!");
            if (nextLevel != null){
                nextLevel.battle(challengerPower);
            }
        } else{
            System.out.println("Just a step away...");
        }
    }

    @Override
    public void setNextLevel(Enemy next) {
        this.nextLevel = next;
    }
}
```

### 2.3 Client
```java
public class ChainDemo {
    public static void main(String[] args) {
        GymLeader gymLeader = new GymLeader();
        EliteFour eliteFour = new EliteFour();
        Champion champion = new Champion();

        // 构建责任链
        gymLeader.setNextLevel(eliteFour);
        eliteFour.setNextLevel(champion);

        // 第一位挑战者，实力是9
        System.out.println("Challenger: Satoshi");
        gymLeader.battle(9);
        System.out.println("\n");

        System.out.println("Challenger: Pocket");
        gymLeader.battle(17);
        System.out.println("\n");

        System.out.println("Challenger: Shigeru");
        gymLeader.battle(5);
        System.out.println("\n");

    }
}
```

### 2.4 测试结果
```
Challenger: Satoshi
Beat gym leaders!!
Beat the Elite Four!!
Just a step away...


Challenger: Pocket
Beat gym leaders!!
Beat the Elite Four!!
Beat champion!!


Challenger: Shigeru
Beat gym leaders!!
Lost to the Elite Four
```

## 3 扩展
一个优秀的设计模式，一定会遵循四大原则，对于责任链模式，当功能需要扩展的时候，应该怎么做？

比如，现在在地区冠军之上，还有地区冠军才能参加的竞标赛，还有个总冠军；亦或者，在道馆馆主和四天王之间，还有一场选拔赛，这里有新的对手需要打败。
该设计模式能做到只扩展不修改吗？ 是可以的，只需要继承Enemy接口，实现新的实现类，再在设置责任链的时候将新的实现类加入即可。

这也能引入责任链模式的优缺点，优点：
- 命令发出者和接收者解耦
- 一个命令可以被责任链中的多个接受者处理

缺点：
- 当实现类变多，链的长度变长，影响效率
- 组成责任链时，长度太长容易出错，或不合理

## 4 应用

- Java日志库 (Log4j)
- Servlet过滤器链: Web应用中，HTTP请求被接收后，在处理请求前，要经过多个过滤器处理，这些过滤器形成一个责任链
