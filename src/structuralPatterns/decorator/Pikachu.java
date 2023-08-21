package structuralPatterns.decorator;

public class Pikachu implements Pokemon{
    @Override
    public void sleep() {
        System.out.println("Pikachu is sleeping...");
    }

    @Override
    public void eat() {
        System.out.println("Pikachu is eating...");
    }

    @Override
    public void spell() {
        System.out.println("Pikachu is spelling...");
    }
}
