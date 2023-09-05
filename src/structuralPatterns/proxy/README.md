# 代理模式

代理模式是一种结构型设计模式。
对于代理模式，其实不难理解，就是甲乙双方在做一件事的时候，有一个中间人作为代理。
甲委托代理，代理和乙对接。生活中的例子就是租房、房东、中介的关系，租房和房东作为甲乙双方，通过中介完成业务。

在代码开发中，代理模式主要用于控制对对象的访问，通过中介，避免调用者和提供方法的对象直接接触。

## 1.情景模拟
代理模式的主要抽象思路就是：A和B的直接交互变为A和B通过C来交互。
在宝可梦没血的时候，我们会选择对其进行治疗，我们可以通过背包里的伤药(直接接触)，或者宝可梦中心(通过代理)。
于是我们首先抽象出代理模式的第一个概念: `Subject`抽象主题——回血，以及`Real Subject`真实主题——实现类
```java
/**
 * 休息的地方
 * 提供回血方法
 */
public interface Rest {
    void heal();
}

/**
 * 回血的具体实现类
 */
public class RestImpl implements Rest{
    @Override
    public void heal() {
        System.out.println("治疗...");
    }
}
```
宝可梦中心作为代理类，自然要先懂得业务，所以代理类也要实现对应的接口：
```java
/**
 * 静态代理类
 */
public class PokemonCenterProxy implements Rest{
    // 被代理的角色
    private Rest rest;

    public PokemonCenterProxy(Rest rest) {
        this.rest = rest;
    }

    @Override
    public void heal() {
        System.out.println("在宝可梦中心...");
        rest.heal();
    }
}
```
### 1.1静态代理

这样，当主角(即程序调用者)想要回血的时候，我们可以直接找到宝可梦中心(代理类)：
```java
public class StaticProxyDemo {
    public static void main(String[] args) {
        // 真实主题
        RestImpl rest = new RestImpl();
        // 传入代理类
        PokemonCenterProxy pokemonCenterProxy = new PokemonCenterProxy(rest);
        // 代理类来执行方法
        pokemonCenterProxy.heal();
    }
}
```
```
在宝可梦中心...
治疗...
```
#### 优点
可以发现，我们仍然调用了原有对象的heal()方法，但是我们在此基础上，完成了方法的扩展。
即我们在没有修改原有实现类的基础上，实现了新增执行前后的动作的功能，我们甚至可以：
```java
    @Override
    public void heal() {
        System.out.println("在宝可梦中心...");
        rest.heal();
        System.out.println("按摩SPA");
    }
```
可能大家看到这里就比较眼熟，这不可以实现日志功能吗？没错，我们可以在方法执行前后织入另外的行为。
这样做的局限也很明显。

#### 局限
从代理类的代码可以看出，我提供了一个构造方法，传入`Rest`接口的实现类，这样避免了有新的实现类的时候要再写对应的新的静态代理类的情况。
这样做的问题在于，我们仍然把被代理类给暴露出来了，仍然要先new一个Rest的实现类。
其次，如果`Rest`接口的方法增多，作为继承了接口的静态代理类，仍要实现每个方法，可能之间有大量的冗余代码。

所以要解决以上局限，动态代理是个更好的选择。

### 1.2 动态代理
Java对动态代理提供了支持。
要实现动态代理，第一步是实现内置的`InvocationHandler`接口，重写`Invoke`方法

```java
public class DynamicProxy implements InvocationHandler {
    private Rest rest;

    public DynamicProxy(Rest rest) {
        this.rest = rest;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入宝可梦中心");
        Object invoke = method.invoke(rest, args);
        System.out.println("售后服务");
        return invoke;
    }
}
```

基于反射，程序在运行途中获得了类的方法(invoke的参数中的method)，方法的参数等信息。
被代理的类，运行的所有方法都被替换为这个`invoke`方法，真正执行原方法的逻辑是在`method.invoke(rest, args);`，
所以这一行的前后，就可以写代理类在执行方法之前/之后的逻辑。

```java
public class DynamicProxyDemo {
    public static void main(String[] args) {
        // 需要被代理的对象
        Rest rest = new RestImpl();
        // 以此创建代理类
        DynamicProxy dynamicProxy = new DynamicProxy(rest);

        ClassLoader classLoader = rest.getClass().getClassLoader();

        Rest o = (Rest) Proxy.newProxyInstance(classLoader, new Class[]{Rest.class}, dynamicProxy);
        o.heal();
    }
}
```

动态代理的核心就在于`Proxy`类，在动态代理中，创建真实对象的实例是通过Proxy的`newProxyInstance`方法。
其参数有三：
- 类加载器：接口类的类加载器，直接调用api获取
- 实现的接口的数组: `new Class[]{...}`是创建数组并赋值的语法，里面传入要实现的接口的类对象
- 实现了`InvocationHandler`的对象，用来执行逻辑

然后用Proxy类创建出的对象调用方法，就可以实现代理类中实现的逻辑，无论`Rest`接口有多少方法，我们都不需要一一去实现。
相应地，有新业务接口的时候，也不用新增代理类。除非你有不同的代理逻辑(即invoke方法里的逻辑)，否则都不需要新增代码。
运行结果：
```
进入宝可梦中心
治疗...
售后服务
```

## 2.应用
Spring框架中AOP就是基于动态代理，以此来实现一种切面逻辑。
应用场景包括：日志记录、权限控制。

## 3.局限
通过Proxy类来实现动态代理有一个最主要的局限：只能代理接口类。
并且通过反射来实现的性能开销比较大。

## 4.解决方案
通过CGLIB实现动态代理。CGLIB可以实现对类的动态代理，并且实现原理是生成新的字节码类。
第一步：引入依赖
```shell
<dependency>
      <groupId>cglib</groupId>
      <artifactId>cglib</artifactId>
      <version>3.3.0</version>
</dependency>
```
第二部：写要代理的类（这里可以不是接口了）
```java
public class Heal {
    public void heal(){
        System.out.println("HP+++");
    }
}
```
第三步：创建代理类
```java
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 代理逻辑
        System.out.println("Pre");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("Suf");
        return o1;
    }
}
```

第四步：创建代理对象
```java
public class Demo {
    public static void main(String[] args) {
        // 创建Enhancer类，类似于Proxy类
        Enhancer enhancer = new Enhancer();
        // 设置目标类
        enhancer.setSuperclass(Heal.class);
        // 设置拦截器
        enhancer.setCallback(new MyMethodInterceptor());
        // 创建代理对象
        Heal proxy = (Heal)enhancer.create();

        // 执行原有方法
        proxy.heal();
    }
}
```

#### 踩坑注意!!
如果跟我一样用的Java17, 那么运行的时候会出现:
```
Exception in thread "main" java.lang.ExceptionInInitializerError
	at com.example.springbootdemo.proxyCglib.Demo.main(Demo.java:7)
Caused by: net.sf.cglib.core.CodeGenerationException: java.lang.reflect.InaccessibleObjectException-->Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @14899482
	at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:464)
	at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:339)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:96)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:94)
	at net.sf.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
	at net.sf.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
	at net.sf.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
	at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:119)
	at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:294)
	at net.sf.cglib.core.KeyFactory$Generator.create(KeyFactory.java:221)
	at net.sf.cglib.core.KeyFactory.create(KeyFactory.java:174)
	at net.sf.cglib.core.KeyFactory.create(KeyFactory.java:153)
	at net.sf.cglib.proxy.Enhancer.<clinit>(Enhancer.java:73)
	... 1 more
```

解决方法和原因: https://blog.csdn.net/guoshengkai373/article/details/127319933

只能换到低版本的JDK，我试过把CGLIB依赖版本弄到最新，也没用。
