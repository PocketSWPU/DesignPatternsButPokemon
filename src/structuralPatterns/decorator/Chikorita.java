package structuralPatterns.decorator;

public class Chikorita implements Pokemon{
    @Override
    public void sleep() {
        System.out.println("Chikorita is sleeping...");
    }

    @Override
    public void eat() {
        System.out.println("Chikorita is eating...");
    }

    @Override
    public void spell() {
        System.out.println("Chikorita is spelling...");
    }
}
