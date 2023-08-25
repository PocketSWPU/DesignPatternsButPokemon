package BehavioralPatterns.chainOfResponsibility;

public class ChainDemo {
    public static void main(String[] args) {
        GymLeader gymLeader = new GymLeader();
        EliteFour eliteFour = new EliteFour();
        Champion champion = new Champion();

        // 构建责任链
        gymLeader.setNextLevel(eliteFour);
        eliteFour.setNextLevel(champion);

        //
        System.out.println("Challenger: Satoshi");
        gymLeader.battle(9);
        System.out.println("\n");

        System.out.println("Challenger: Pocket");
        gymLeader.battle(17);
        System.out.println("\n");

        System.out.println("Challenger: Shigeru");
        gymLeader.battle(5);
        System.out.println("\n");

    }
}
