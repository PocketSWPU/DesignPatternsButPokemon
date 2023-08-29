# 建造者模式
建造者模式是一种创建型模式，主要针对于某一个类有特别繁杂的属性，并且这些属性中有部分不是必须的。
避免在创建对象时，需要众多的构造函数，就有了建造者模式。

比如说，我现在有一个果篮，我可以放苹果、香蕉、梨等各种水果进去构造一个果篮，但是会出现什么状况呢？
我可能今天只有苹果，或者只有香蕉，也有可能我今天只有其中的两种。那么，有3个属性的情况下，会出现共6种情况。
这时候有2种解决办法：1.针对所有情况，写6个不同参数的构造函数；2.写一个全参构造方法，在没有某种水果时传入0或者null。

第一种方法，在属性变多的时候，构造函数直接变成海量；第二种方法，需要记住参数的顺序，容易出错。

（吐槽：要是可以像python一样，把参数名指定一下就好了：比如
`
plt.plot(data_x, data_y1, label="IUG-CF", marker='*')
`
）

那么针对这种“对象的属性过多，又有些属性不必须”的时候，建造者模式是一个好的选择。（就像最近投简历，简历由众多模块组成，可能就没有实习经历，或者项目经历）

## 1.情景模拟
小智和小霞在旅行，路过西柚市，这里在举办一个多属性大师的比赛，规则是每种属性的宝可梦只能带一只，组成一个队伍参加比赛。
我们把宝可梦组成的队伍抽象成一个类，每种属性的宝可梦作为类的属性。
于是有了`Product`：
```java
/**
 * Product
 * 一个多属性队伍由多个属性的宝可梦组成
 * 省略了Pokemon接口，直接用String
 */
public class MultiTypeTeam {
    private String fireType; // 火属性
    private String waterType; // 水属性
    private String flyingType; // 飞行系
    private String grassType; // 草属性
    private String electricType; // 电属性
    // ...
    // setter + toString 省略
}
```

小智和小霞找到了比赛的报名入口，这里是一个建造者的抽象建造类：
```java
/**
 * 抽象建造者Builder
 * 为每个属性的构建提供方法
 */
public interface TeamBuilder {
    void chooseFireType();
    void chooseWaterType();
    void chooseFlyingType();
    void chooseGrassType();
    void chooseElectricType();
}
```

小智的队伍很快构建出来了：
```java
public class SatoshiTeamBuilder implements TeamBuilder{
    private MultiTypeTeam multiTypeTeam;
    public SatoshiTeamBuilder() {
        this.multiTypeTeam = new MultiTypeTeam();
    }
    @Override
    public void chooseFireType() {
        multiTypeTeam.setFireType("喷火龙");
    }

    @Override
    public void chooseWaterType() {
        multiTypeTeam.setWaterType("杰尼龟");
    }

    @Override
    public void chooseFlyingType() {
        multiTypeTeam.setFlyingType("巴大蝴");
    }

    @Override
    public void chooseGrassType() {
        multiTypeTeam.setGrassType("妙蛙种子");
    }

    @Override
    public void chooseElectricType() {
        multiTypeTeam.setElectricType("皮卡丘");
    }

    @Override
    public MultiTypeTeam createTeam() {
        return this.multiTypeTeam;
    }
}
```

小霞直接无语了，谁懂啊家人们，我一个水属性大师，当然只有水属性
```java
public class MistyTeamBuilder implements TeamBuilder{
    private MultiTypeTeam multiTypeTeam;
    public MistyTeamBuilder() {
        this.multiTypeTeam = new MultiTypeTeam();
    }
    @Override
    public void chooseFireType() {
        
    }

    @Override
    public void chooseWaterType() {
        multiTypeTeam.setWaterType("宝石海星");
    }
    // ......

    @Override
    public MultiTypeTeam createTeam() {
        return this.multiTypeTeam;
    }
}
```

再定义一个指挥者，实现使用者和对象创建过程的解耦：
```java
/**
 * 指挥者
 * 指挥构造，实现解耦，让获取者无需关心对象怎么构建的
 */
public class Director {
    public MultiTypeTeam buildTeam(TeamBuilder teamBuilder){
        teamBuilder.chooseElectricType();
        teamBuilder.chooseFireType();
        teamBuilder.chooseFlyingType();
        teamBuilder.chooseWaterType();
        teamBuilder.chooseGrassType();
        return teamBuilder.createTeam();
    }
}
```

测试类：
```java
public class BuilderDemo {
    public static void main(String[] args) {
        Director director = new Director();
        MultiTypeTeam satoshiTeam = director.buildTeam(new SatoshiTeamBuilder());
        MultiTypeTeam mistyTeam = director.buildTeam(new MistyTeamBuilder());

        System.out.println(satoshiTeam.toString());
        System.out.println(mistyTeam.toString());
    }
}
```

```
MultiTypeTeam{fireType='喷火龙', waterType='杰尼龟', flyingType='巴大蝴', grassType='妙蛙种子', electricType='皮卡丘'}
MultiTypeTeam{fireType='null', waterType='宝石海星', flyingType='null', grassType='null', electricType='null'}
```

稍微总结一下，建造者模式的四个角色：

- 产品 (Product): 要建造的类
- 抽象建造者 (Builder): 提供建造需要的方法
- 具体建造者 (Concrete Builder): 实现建造
- 指挥者 (Director): 调度建造方法，实现解耦，每次使用对象时只需通过Director，不需要知道怎么构建的

上面的实现方法，其实可以让使用者自己来进行按需构建，从而省略指挥者的角色。
并且，修改其中某个参数的时候，需要写一个全新的实现类，这很麻烦。
于是，通过查阅资料，有一种对传统建造者方法的改进，也称为简化版。

## 2. 改进实现
顺便在这里做一个改进，我们将水属性宝可梦设置为必须的，以区分必须的属性和非必须的属性，然后将建造的方法的返回类型改为建造者
```java
/**
 * 返回一个产品对象
 */
public interface TeamBuilderSimple {
    TeamBuilderSimple chooseFireType(String pokemon);
    TeamBuilderSimple chooseGrassType(String pokemon);
    TeamBuilderSimple chooseFlyingType(String pokemon);
    TeamBuilderSimple chooseElectricType(String pokemon);
    MultiTypeTeam getTeam();
}
```

那么具体的构造类只需要实现方法：
```java
/**
 * 自行传入
 */
public class ConcreteTeamBuilderSimple implements TeamBuilderSimple{
    MultiTypeTeam multiTypeTeam;

    ConcreteTeamBuilderSimple(String waterPokemon){
        multiTypeTeam = new MultiTypeTeam();
        multiTypeTeam.setWaterType(waterPokemon);
    }

    @Override
    public TeamBuilderSimple chooseFireType(String pokemon) {
        multiTypeTeam.setFireType(pokemon);
        return this;
    }

    @Override
    public TeamBuilderSimple chooseGrassType(String pokemon) {
        multiTypeTeam.setGrassType(pokemon);
        return this;
    }

    @Override
    public TeamBuilderSimple chooseFlyingType(String pokemon) {
        multiTypeTeam.setFlyingType(pokemon);
        return this;
    }

    @Override
    public TeamBuilderSimple chooseElectricType(String pokemon) {
        multiTypeTeam.setElectricType(pokemon);
        return this;
    }

    @Override
    public MultiTypeTeam getTeam() {
        return multiTypeTeam;
    }
}
```

于是要使用的时候，就更加方便，通过一种链式调用自行构建对象，且不需要写繁杂的构造函数
```java
public class BuilderDemo02 {
    public static void main(String[] args) {
        MultiTypeTeam satoshiTeam = new ConcreteTeamBuilderSimple("小锯鳄")
                .chooseElectricType("皮卡丘")
                .chooseFireType("暖暖猪")
                .chooseFlyingType("急冻鸟")
                .chooseGrassType("菊草叶")
                .getTeam();

        MultiTypeTeam mistyTeam = new ConcreteTeamBuilderSimple("暴鲤龙")
                .getTeam();

        System.out.println(satoshiTeam.toString());
        System.out.println(mistyTeam.toString());
    }
}
```

```
MultiTypeTeam{fireType='暖暖猪', waterType='小锯鳄', flyingType='急冻鸟', grassType='菊草叶', electricType='皮卡丘'}
MultiTypeTeam{fireType='null', waterType='暴鲤龙', flyingType='null', grassType='null', electricType='null'}
```

## 3.应用

### 3.1 Swagger
写过web应用的读者们应该接触过Swagger, 通过SpringBoot整合Swagger的时候，会编写一个配置类，我们来看看他的代码：
```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("GoCooking")
                        .description("GoCooking API文档")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                        .externalDocs(new ExternalDocumentation()
                        .description("外部文档")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
```
`OpenAPI`类中有个`info`属性, 通过`info()`方法来设置`info`
```java
public OpenAPI info(Info info) {
        this.info = info;
        return this;
    }
```
而`Info`类的构建过程，就是本文改进版的写法，比如下面两个方法
```java
    public Info description(String description) {
        this.description = description;
        return this;
    }

    public Info title(String title) {
        this.title = title;
        return this;
        }
```

### 3.2 StringBuilder

StringBuilder使用`append()`方法后，返回的还是同一个对象，这也是建造者模式的应用

```java
public StringBuilder append(String str) {
        super.append(str);
        return this;
    }
```

