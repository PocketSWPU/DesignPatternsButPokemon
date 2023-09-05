package structuralPatterns.proxy.staticProxy;

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
