package structuralPatterns.decorator;

abstract class PokemonDecorator implements Pokemon {
    Pokemon pokemon;

    public PokemonDecorator(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public void sleep() {
        pokemon.sleep();
    }

    @Override
    public void eat() {
        pokemon.eat();
    }

    @Override
    public void spell() {
        pokemon.spell();
    }
}
