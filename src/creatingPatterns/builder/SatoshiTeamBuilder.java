package creatingPatterns.builder;

public class SatoshiTeamBuilder implements TeamBuilder{
    private MultiTypeTeam multiTypeTeam;

    public SatoshiTeamBuilder() {
        this.multiTypeTeam = new MultiTypeTeam();
    }

    @Override
    public void chooseFireType() {
        multiTypeTeam.setFireType("喷火龙");
    }

    @Override
    public void chooseWaterType() {
        multiTypeTeam.setWaterType("杰尼龟");
    }

    @Override
    public void chooseFlyingType() {
        multiTypeTeam.setFlyingType("巴大蝴");
    }

    @Override
    public void chooseGrassType() {
        multiTypeTeam.setGrassType("妙蛙种子");
    }

    @Override
    public void chooseElectricType() {
        multiTypeTeam.setElectricType("皮卡丘");
    }

    @Override
    public MultiTypeTeam createTeam() {
        return this.multiTypeTeam;
    }
}
