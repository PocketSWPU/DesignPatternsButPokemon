package structuralPatterns.adapter;

public class AdapterDemo {
    public static void main(String[] args) {
        EmeraldVersion emeraldVersion = new EmeraldVersion();
        emeraldVersion.usePokemon();

        NsGame pokemon = new ScarletVersion();
        pokemon.usePokemon("gbaData");
        pokemon.usePokemon("nsData");
    }
}
