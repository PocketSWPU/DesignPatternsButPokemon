package creatingPatterns.builder;

public class MistyTeamBuilder implements TeamBuilder{
    private MultiTypeTeam multiTypeTeam;
    public MistyTeamBuilder() {
        this.multiTypeTeam = new MultiTypeTeam();
    }
    @Override
    public void chooseFireType() {

    }

    @Override
    public void chooseWaterType() {
        multiTypeTeam.setWaterType("宝石海星");
    }

    @Override
    public void chooseFlyingType() {

    }

    @Override
    public void chooseGrassType() {

    }

    @Override
    public void chooseElectricType() {

    }

    @Override
    public MultiTypeTeam createTeam() {
        return this.multiTypeTeam;
    }
}
