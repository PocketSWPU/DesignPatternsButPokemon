package BehavioralPatterns.observer;

public class Satoshi extends ObserverTrainer{

    // 该对象观察这个Subject
    // 同时Subject也绑定这个对象，会发送通知
    public Satoshi(SubjectPokedex pokedex) {
        this.subject = pokedex;
        this.subject.employ(this);
    }

    @Override
    public void update() {
        System.out.println("I am Satoshi! I got the message:");
        this.subject.getDexState("Kanto");
    }
}
