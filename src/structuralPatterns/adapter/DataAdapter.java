package structuralPatterns.adapter;

/**
 * Pokemon Home
 */
public class DataAdapter implements GbaGame{
    GbaGame gbaGame; // 旧世代

    public DataAdapter(GbaGame gbaGame) {
        this.gbaGame = gbaGame;
    }

    @Override
    public void usePokemon(String dataFormat, String newVersion) {
        if("gbaData".equals(dataFormat)){
            gbaGame.usePokemon(dataFormat, newVersion);
        }
    }
}
