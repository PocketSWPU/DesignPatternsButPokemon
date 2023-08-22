package structuralPatterns.flyweight;

/**
 * 宝可梦具体实现类
 * 对应具体享元类
 * ConcreteFlyweight
 */
public class ConcretePokemon implements Pokemon{
    String name;
    Integer hp = 100;

    public ConcretePokemon(String name) {
        this.name = name;
    }

    @Override
    public void go() {
        System.out.println("去吧!" + this.name + "! (HP:" + this.hp + ")");
    }

    @Override
    public void loseHP(Integer damage) {
        this.hp -= damage;
    }
}
