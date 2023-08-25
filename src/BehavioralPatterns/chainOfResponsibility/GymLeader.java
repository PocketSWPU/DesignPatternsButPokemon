package BehavioralPatterns.chainOfResponsibility;

/**
 * 馆主
 */
public class GymLeader implements Enemy{
    private Enemy nextLevel;
    int power = 4;
    @Override
    public void battle(int challengerPower) {
        if (challengerPower >= power){
            System.out.println("Beat gym leaders!!");
            if (nextLevel != null){
                nextLevel.battle(challengerPower);
            }
        } else{
            System.out.println("Lost to gym leaders...");
        }
    }

    @Override
    public void setNextLevel(Enemy next) {
        this.nextLevel = next;
    }
}
