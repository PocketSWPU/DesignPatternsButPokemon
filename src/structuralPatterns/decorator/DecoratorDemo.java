package structuralPatterns.decorator;

public class DecoratorDemo {
    public static void main(String[] args) {
        Pokemon chikorita = new Chikorita();
        Pokemon pikachu = new Pikachu();

        DymaxPokemon dymaxPikachu = new DymaxPokemon(pikachu);
        MegaPokemon megaPikachu = new MegaPokemon(pikachu);
        MegaPokemon megaChikorita = new MegaPokemon(chikorita);

        pikachu.spell();
        dymaxPikachu.dymaxSpell();
        megaPikachu.megaSpell();
        megaChikorita.megaSpell();
    }
}
