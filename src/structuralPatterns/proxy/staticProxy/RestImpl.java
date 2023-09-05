package structuralPatterns.proxy.staticProxy;

/**
 * 回血的具体实现类
 */
public class RestImpl implements Rest{
    @Override
    public void heal() {
        System.out.println("治疗...");
    }
}
