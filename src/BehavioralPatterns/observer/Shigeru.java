package BehavioralPatterns.observer;

public class Shigeru extends ObserverTrainer{
    public Shigeru(SubjectPokedex subject) {
        this.subject = subject;
        this.subject.employ(this);
    }

    @Override
    public void update() {
        System.out.println("I am Shigeru! I got the message:");
        this.subject.getDexState("Johto");
    }
}
