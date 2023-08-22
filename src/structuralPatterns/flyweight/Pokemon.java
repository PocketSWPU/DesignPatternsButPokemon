package structuralPatterns.flyweight;

/**
 * 宝可梦抽象类
 * 对应抽象享元类
 * Flyweight
 */
public interface Pokemon {
    /**
     * 宝可梦出场
     */
    void go();

    /**
     * 宝可梦在战斗中损失HP
     * @param damage HP
     */
    void loseHP(Integer damage);
}
