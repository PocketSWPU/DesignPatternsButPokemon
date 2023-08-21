package BehavioralPatterns.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubjectPokedex implements SubjectLab{
    private List<ObserverTrainer> trainers = new ArrayList<>();
    private HashMap<String, Integer> pokedex = new HashMap<>();
    private Integer num;

    /**
     * 初始化，假设各个地区已经发现了很多宝可梦
     * 还有value个宝可梦没有被抓到研究所研究
     * num统计总数
     */
    public SubjectPokedex() {
        pokedex.put("Kanto", 155);
        pokedex.put("Johto", 155);
        num = 310;
    }

    /**
     * 打印某地区剩下的宝可梦
     * @param region 地区
     */
    public void getDexState(String region){
        System.out.println("There are still " + pokedex.getOrDefault(region, 0) + " Pokemons in " +
                region + " that haven't been captured.");
        System.out.println("There are a total of " + num +" Pokemon that have not been captured yet.\n");
    }

    /**
     * 抓获了num个宝可梦
     */
    public void catchPokemon(String region, Integer num){
        this.pokedex.put(region, pokedex.get(region) - num);
        this.num -= num;
        notifyAllTrainers();
    }

    /**
     * 任命愿意来帮助捕捉宝可梦的训练家们
     * @param trainer 训练家
     */
    public void employ(ObserverTrainer trainer){
        trainers.add(trainer);
    }

    /**
     * 通知所有参与的训练家
     */
    public void notifyAllTrainers(){
        for (ObserverTrainer trainer : trainers) {
            trainer.update();
        }
    }
}
