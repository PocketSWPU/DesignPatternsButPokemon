package BehavioralPatterns.chainOfResponsibility;

/**
 * 冠军
 */
public class Champion implements Enemy{
    private Enemy nextLevel;
    int power = 15;
    @Override
    public void battle(int challengerPower) {
        if (challengerPower >= power){
            System.out.println("Beat champion!!");
            if (nextLevel != null){
                nextLevel.battle(challengerPower);
            }
        } else{
            System.out.println("Just a step away...");
        }
    }

    @Override
    public void setNextLevel(Enemy next) {
        this.nextLevel = next;
    }
}
