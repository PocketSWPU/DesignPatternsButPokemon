package creatingPatterns.builder;

/**
 * 返回一个产品对象
 */
public interface TeamBuilderSimple {
    TeamBuilderSimple chooseFireType(String pokemon);
    TeamBuilderSimple chooseGrassType(String pokemon);
    TeamBuilderSimple chooseFlyingType(String pokemon);
    TeamBuilderSimple chooseElectricType(String pokemon);
    MultiTypeTeam getTeam();
}
