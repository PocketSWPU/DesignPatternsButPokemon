package structuralPatterns.decorator;

public class MegaPokemon extends PokemonDecorator{
    public MegaPokemon(Pokemon pokemon) {
        super(pokemon);
    }

    public void megaSpell(){
        super.spell();
        System.out.println("Mega Power!");
    }
}
