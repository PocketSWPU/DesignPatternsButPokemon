package structuralPatterns.flyweight;

import java.util.HashMap;

/**
 * 模拟一场战斗的精灵背包
 * 对应享元工厂类
 * FlyweightFactory
 */
public class FlyweightFactory {
    // 用一个哈希表判断对象是否存在
    private HashMap<String, Pokemon> pokemonPool = new HashMap<>();

    /**
     * 获取宝可梦，如果没有出场过，就创建对象
     * 出场过的就返回之前创建的对象
     * @param name
     * @return
     */
    public Pokemon getPokemon(String name){
        // 用名字获取对象 存在就取出 不存在就创建后存入哈希表
        if(!pokemonPool.containsKey(name)){
            pokemonPool.put(name, new ConcretePokemon(name));
        }
        Pokemon pokemon = pokemonPool.get(name);
        pokemon.go();
        return pokemon;
    }
}
