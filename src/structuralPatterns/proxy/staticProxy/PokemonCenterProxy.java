package structuralPatterns.proxy.staticProxy;

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
