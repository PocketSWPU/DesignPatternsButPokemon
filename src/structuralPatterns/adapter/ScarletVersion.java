package structuralPatterns.adapter;

public class ScarletVersion implements NsGame{

    DataAdapter dataAdapter;
    public void usePokemon(String dataFormat){
        if ("gbaData".equals(dataFormat)){
            dataAdapter = new DataAdapter(new EmeraldVersion());
            dataAdapter.usePokemon(dataFormat, "Switch");
        }else if ("nsData".equals(dataFormat)){
            // 内置功能，使用当前版本的宝可梦
            System.out.println("Go! Chikorita (Get In Scarlet Version)");
        }
    }
}
