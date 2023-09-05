package structuralPatterns.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Demo<T> implements InvocationHandler {
    private T obj;

    public Demo(T obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before");
        Object result = method.invoke(obj, args);
        System.out.println("After");
        return result;
    }

    public static void main(String[] args) {
        Demo<String> objectDemo = new Demo<>(new String("abs"));
        String o = (String)Proxy.newProxyInstance(CharSequence.class.getClassLoader(), new Class<?>[]{CharSequence.class}, objectDemo);
        System.out.println(o.equals("2"));
    }
}
