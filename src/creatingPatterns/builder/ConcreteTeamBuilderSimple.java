package creatingPatterns.builder;

/**
 * 自行传入
 */
public class ConcreteTeamBuilderSimple implements TeamBuilderSimple{
    MultiTypeTeam multiTypeTeam;

    ConcreteTeamBuilderSimple(String waterPokemon){
        multiTypeTeam = new MultiTypeTeam();
        multiTypeTeam.setWaterType(waterPokemon);
    }

    @Override
    public TeamBuilderSimple chooseFireType(String pokemon) {
        multiTypeTeam.setFireType(pokemon);
        return this;
    }

    @Override
    public TeamBuilderSimple chooseGrassType(String pokemon) {
        multiTypeTeam.setGrassType(pokemon);
        return this;
    }

    @Override
    public TeamBuilderSimple chooseFlyingType(String pokemon) {
        multiTypeTeam.setFlyingType(pokemon);
        return this;
    }

    @Override
    public TeamBuilderSimple chooseElectricType(String pokemon) {
        multiTypeTeam.setElectricType(pokemon);
        return this;
    }

    @Override
    public MultiTypeTeam getTeam() {
        return multiTypeTeam;
    }
}
