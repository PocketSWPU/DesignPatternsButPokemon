package creatingPatterns.builder;

/**
 * 指挥者
 * 指挥构造，实现解耦，让获取者无需关心对象怎么构建的
 */
public class Director {
    public MultiTypeTeam buildTeam(TeamBuilder teamBuilder){
        teamBuilder.chooseElectricType();
        teamBuilder.chooseFireType();
        teamBuilder.chooseFlyingType();
        teamBuilder.chooseWaterType();
        teamBuilder.chooseGrassType();
        return teamBuilder.createTeam();
    }
}
