## 1. 装饰模式

为了不改变组件的结构，动态地扩展其功能。

通常，扩展功能通过子类进行，但是继承的方式具有静态特征，耦合度高。



意图：动态地给对象添加额外的功能

主要解决：继承方式是静态特征，扩展的功能多的时候，子类会太膨胀

何时使用：不想增加很多子类的时候扩展类

### 假设情景

一个宝可梦，可以睡觉，吃饭，放技能；如果要扩展它的行为，比如Mega进化后，假设有专属的Mega技能，然后极巨化，可以放极巨技能。
如果用继承的方法实现代码，那么每种宝可梦不都得写对应的Mega类和极巨化类？当发现一种新宝可梦的时候，那又得增加对应的子类，比较繁琐。
那么就可以定义一个装饰器接口，以便对新的变化进行扩展，比如太晶化。通过实现装饰器接口，构造函数中包含需要被装饰的宝可梦，就实现了解耦。
不管你是新的宝可梦也好，还是旧的1000+种也好，我只需要写一个具体的实现类，这个实现类中可以通过super执行原有的方法，也可以扩展专属的装饰方法。

![image](https://github.com/PocketSWPU/DesignPatternsButPokemon/assets/107466625/c04c0fe4-8c17-4533-9e1c-a8483053b7d4)


**宝可梦接口**：抽象组件（Component）

```java
public interface Pokemon {
    void sleep();

    void eat();

    void spell();
}
```

**宝可梦实现类：**具体组件（Concrete Component）

有皮卡丘和菊草叶两种实现类，作为具体的组件

```java
public class Pikachu implements Pokemon{
    @Override
    public void sleep() {
        System.out.println("Pikachu is sleeping...");
    }

    @Override
    public void eat() {
        System.out.println("Pikachu is eating...");
    }

    @Override
    public void spell() {
        System.out.println("Pikachu is spelling...");
    }
}

public class Chikorita implements Pokemon{
    @Override
    public void sleep() {
        System.out.println("Chikorita is sleeping...");
    }

    @Override
    public void eat() {
        System.out.println("Chikorita is eating...");
    }

    @Override
    public void spell() {
        System.out.println("Chikorita is spelling...");
    }
}
```

**宝可梦变化装饰器** ：抽象装饰（Decorator）

用抽象类方便扩展多种装饰，以应对宝可梦可能有的多种变化

```java
abstract class PokemonDecorator implements Pokemon{
    Pokemon pokemon;

    public PokemonDecorator(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public void sleep() {
        pokemon.sleep();
    }

    @Override
    public void eat() {
        pokemon.eat();
    }

    @Override
    public void spell() {
        pokemon.spell();
    }
}
```

**宝可梦具体变化** ：具体装饰（Concrete Decorator）

实现了Mega进化和极巨化两种变化

```java
public class MegaPokemon extends PokemonDecorator{
    public MegaPokemon(Pokemon pokemon) {
        super(pokemon);
    }

    public void megaSpell(){
        super.spell();
        System.out.println("Mega Power!");
    }
}

public class DymaxPokemon extends PokemonDecorator{
    public DymaxPokemon(Pokemon pokemon) {
        super(pokemon);
    }

    public void dymaxSpell(){
        super.spell();
        System.out.println("Dymax Power!");
    }
}
```

**测试类**

```java
public class DecoratorDemo {
    public static void main(String[] args) {
        Pokemon chikorita = new Chikorita();
        Pikachu pikachu = new Pikachu();

        DymaxPokemon dymaxPikachu = new DymaxPokemon(pikachu);
        MegaPokemon megaPikachu = new MegaPokemon(pikachu);
        MegaPokemon megaChikorita = new MegaPokemon(chikorita);

        pikachu.spell();
        dymaxPikachu.dymaxSpell();
        megaPikachu.megaSpell();
        megaChikorita.megaSpell();
    }
}
```

```
Pikachu is spelling...
Pikachu is spelling...
Dymax Power!
Pikachu is spelling...
Mega Power!
Chikorita is spelling...
Mega Power!
```



至此，再回头看是否实现我们的意图：动态地给一个对象添加一些额外的职责。

本来宝可梦只有吃饭睡觉放技能，但是随着版本更迭，宝可梦还增加了Mega进化，极巨化，在不用子类扩展的情况下，可以使用装饰模式，当需要扩展新功能的时候，比如在朱紫版本增加了太晶化，我们只需要新写一个PokemonDecorator的子类`CrystalPokemon`，实现新功能（不还是用子类扩展了吗。。。），所以装饰模式的缺点就是：装饰类太多的时候比较复杂（。。。）
