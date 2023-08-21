package BehavioralPatterns.observer;

public class ObserverDemo {
    public static void main(String[] args) {
        // Subject对象
        SubjectPokedex subjectPokedex = new SubjectPokedex();

        // 两个观察者，接收Subject的通知
        new Satoshi(subjectPokedex);
        new Shigeru(subjectPokedex);

        // 通知：关都地区抓住了30只
        subjectPokedex.catchPokemon("Kanto", 30);

        // 通知：城都地区抓住了90只
        subjectPokedex.catchPokemon("Johto", 90);
    }
}
