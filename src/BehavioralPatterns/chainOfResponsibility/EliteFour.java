package BehavioralPatterns.chainOfResponsibility;

/**
 * 四天王
 */
public class EliteFour implements Enemy{
    private Enemy nextLevel;
    int power = 9;
    @Override
    public void battle(int challengerPower) {
        if (challengerPower >= power){
            System.out.println("Beat the Elite Four!!");
            if (nextLevel != null){
                nextLevel.battle(challengerPower);
            }
        }else {
            System.out.println("Lost to the Elite Four");
        }
    }

    @Override
    public void setNextLevel(Enemy next) {
        this.nextLevel = next;
    }
}
