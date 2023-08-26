# 适配器模式

意图：将一个类的接口转换成客户希望的另一个接口

主要解决：把现有对象放到新环境里，而新环境要求的接口，现有对象不满足

何时使用：现有的类被需要，而这个类的接口不符合要求；建立一个可复用的类，用于让彼此之间无关的类或未来可能引入的类可以一起工作



适配器模式有3个角色：

- 目标接口 （Target）：当前系统业务期待的接口
- 适配者类：被适配的现有组件的接口
- 适配器类：一个转换器，继承或引用适配者对象，把适配者接口转为目标接口

## 1 情景假设

现在我们假设这样一个场景：小智在绿宝石的存档中，有一只巨tm强的裂空座，性格好，个体高，努力值也刷得完美。现在已经发售了朱紫，他还想用这只裂空座，但是问题来了，绿宝石是GBA主机的游戏，朱紫是Switch主机的游戏，他们存储数据的格式不同啊！这怎么办呢？那么就只能推出一个适配器，把GBA的数据转化成Switch的数据，把裂空座从绿宝石移植到朱紫，可能熟悉宝可梦游戏的朋友已经想到了，Pokemon Home就是干这个事的，所以我们可以把这个故事中的角色抽象为适配器模式需要的三个角色：

- 目标接口：Switch数据
- 适配者类：GBA数据
- 适配器类：Pokemon Home

![image](https://github.com/PocketSWPU/DesignPatternsButPokemon/assets/107466625/67b1fa28-6da5-4860-82ce-060d91f7b801)




## 2 代码示例

![image](https://github.com/PocketSWPU/DesignPatternsButPokemon/assets/107466625/52b658fd-d89e-42d5-90d7-ea201a6dba46)


首先定义一个顶层接口Game

```java
public interface Game {
}
```



现有的接口，就是老版本GBA游戏：

```java
public interface GbaGame extends Game{
    void usePokemon(String dataFormat);
}

public class EmeraldVersion implements GbaGame{
    /**
     * 在绿宝石中使用宝可梦
     */
    public void usePokemon(String dataFormat, String version) {
        System.out.println("Go! Rayquaza! (In " + version + " Version)");
    }

    public void usePokemon() {
        System.out.println("Go! Rayquaza! (In GBA Version )");
    }
}
```

有绿宝石一个实现类，然而，现在游戏已经到了朱紫版本，需要的是Switch上的数据：

```java
public interface NsGame extends Game{
    void usePokemon(String dataFormat);
}

public class ScarletVersion implements NsGame{

    public void usePokemon(String dataFormat){
        if ("nsData".equals(dataFormat)){
            // 内置功能，使用当前版本的宝可梦
            System.out.println("Go! Chikorita (Get In Scarlet Version)");
        }
    }
}
```

于是为了让NS端适配GBA的数据，我们需要一个适配器，这个适配器要有旧版本的属性（因为适配器是为已有的类设计的）

```java
/**
 * Pokemon Home
 */
public class DataAdapter implements GbaGame{
    GbaGame gbaGame; // 旧世代

    public DataAdapter(GbaGame gbaGame) {
        this.gbaGame = gbaGame;
    }

    @Override
    public void usePokemon(String dataFormat) {
        if("gbaData".equals(dataFormat)){
            gbaGame.usePokemon(dataFormat);
        }
    }
}
```

所以，要在新版本（NsGame）中使用旧版本的数据，新版本应该是：

```java
public class ScarletVersion implements NsGame{

    DataAdapter dataAdapter;
    public void usePokemon(String dataFormat){
        if ("gbaData".equals(dataFormat)){
            dataAdapter = new DataAdapter(new EmeraldVersion());
            dataAdapter.usePokemon(dataFormat, "Switch");
        }else if ("nsData".equals(dataFormat)){
            // 内置功能，使用当前版本的宝可梦
            System.out.println("Go! Chikorita (Get In Scarlet Version)");
        }
    }
}
```

**注意：这里并不违背“开闭原则”，因为前面的ScarletVersion类只是为了说明逻辑，并不是对代码进行修改**

测试类

```java
public class AdapterDemo {
    public static void main(String[] args) {
        // 老版本使用
        EmeraldVersion emeraldVersion = new EmeraldVersion();
        emeraldVersion.usePokemon();

        // 新版本使用
        NsGame pokemon = new ScarletVersion();
        pokemon.usePokemon("gbaData");
        pokemon.usePokemon("nsData");
    }
}
```

```
Go! Rayquaza! (In GBA Version )
Go! Rayquaza! (In Switch Version)
Go! Chikorita (Get In Scarlet Version)
```

## 3 应用
> 2023/08/26 更新

在学习多线程八股文的时候，新了解了`FutureTask`类。关于这个类的作用，不在本文中赘述。

`FutureTask`类有2个构造方法：
```java
class FutureTask{
    private Callable<V> callable;
    
    public FutureTask(Callable<V> callable) {
        if (callable == null)
        throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;       // ensure visibility of callable
        }

    public FutureTask(Runnable runnable, V result) {
        this.callable = Executors.callable(runnable, result);
        this.state = NEW;       // ensure visibility of callable
        }
}
    
    
```
可见，`FutureTask`的构造方法参数可以是`Callable`也可以是`Runnable`的实现类，然而，在传入`Runnable`实现类时，
还是对变量`callable`赋值，这是一个`Callable`对象。所以，无论传入什么，最终都变成了`Callable`。
点进`Executors.callable()`:
```java
public static <T> Callable<T> callable(Runnable task, T result) {
        if (task == null)
            throw new NullPointerException();
        return new RunnableAdapter<T>(task, result);
    }
```
可以看到一个`RunnableAdapter`, 一个适配器类，传入Runnable，得到的是继承了`Callable`接口的适配器对象。

## 4 另一个例子

题外话，在学习这个设计模式的时候想到的，在做机器学习的时候，通常有这么个流程：

数据集 -> 数据源 -> 模型 -> ...

数据集的格式各不相同，然而，模型的输入是固定的，我们把数据集转换成能够进入模型的过程通常被叫做**数据预处理**，这是为了让数据集和模型输入的格式适配。比如：

![image](https://github.com/PocketSWPU/DesignPatternsButPokemon/assets/107466625/025b9b1e-6053-403c-b118-73b16e2c80c9)


每个数据集的分割符不同，那么为了提供给模型一个模型能够接受的格式，就可以使用适配器模式，让数据从`csv`或者`dat`格式转换为`data_df`，即`dataframe`对象的过程，也就是适配的过程。
