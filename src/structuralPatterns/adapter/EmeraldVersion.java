package structuralPatterns.adapter;

public class EmeraldVersion implements GbaGame{
    /**
     * 在绿宝石中使用宝可梦
     */
    @Override
    public void usePokemon(String dataFormat, String version) {
        System.out.println("Go! Rayquaza! (In " + version + " Version)");
    }

    public void usePokemon() {
        System.out.println("Go! Rayquaza! (In GBA Version )");
    }
}
