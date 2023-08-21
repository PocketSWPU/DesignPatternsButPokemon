package structuralPatterns.adapter;

public interface GbaGame extends Game{
    void usePokemon(String dataFormat, String version);
}
