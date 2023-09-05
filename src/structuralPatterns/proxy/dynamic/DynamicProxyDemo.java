package structuralPatterns.proxy.dynamic;

import structuralPatterns.proxy.staticProxy.Rest;
import structuralPatterns.proxy.staticProxy.RestImpl;

import java.lang.reflect.Proxy;

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
