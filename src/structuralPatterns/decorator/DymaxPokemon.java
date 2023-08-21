package structuralPatterns.decorator;

public class DymaxPokemon extends PokemonDecorator {
    public DymaxPokemon(Pokemon pokemon) {
        super(pokemon);
    }

    public void dymaxSpell(){
        super.spell();
        System.out.println("Dymax Power!");
    }
}
