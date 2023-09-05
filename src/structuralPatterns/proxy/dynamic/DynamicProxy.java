package structuralPatterns.proxy.dynamic;

import structuralPatterns.proxy.staticProxy.Rest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
