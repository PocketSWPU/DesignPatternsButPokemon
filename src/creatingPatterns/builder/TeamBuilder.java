package creatingPatterns.builder;

/**
 * 抽象建造者Builder
 * 为每个属性的构建提供方法
 */
public interface TeamBuilder {
    void chooseFireType();
    void chooseWaterType();
    void chooseFlyingType();
    void chooseGrassType();
    void chooseElectricType();
    MultiTypeTeam createTeam();
}
