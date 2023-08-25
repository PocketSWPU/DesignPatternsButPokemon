package BehavioralPatterns.chainOfResponsibility;

public interface Enemy {
    /**
     * 接受挑战
     * @param challengerPower 挑战者的能力值
     */
    void battle(int challengerPower);

    /**
     * 设置下一个负责人
     * @param next
     */
    void setNextLevel(Enemy next);
}
