package structuralPatterns.flyweight;

public class FlyWeightDemo {
    public static void main(String[] args) {
        // 用工厂模拟2位对战训练家
        FlyweightFactory satoshi = new FlyweightFactory();
        FlyweightFactory shigeru = new FlyweightFactory();

        // 出场
        Pokemon pikachu = satoshi.getPokemon("皮卡丘");
        Pokemon charizard = shigeru.getPokemon("喷火龙");

        // 战斗
        // 皮卡丘掉了半血
        pikachu.loseHP(50);
        // 喷火龙gg...
        System.out.println("喷火龙倒下了...\n");

        // 第二轮
        satoshi.getPokemon("皮卡丘");
        shigeru.getPokemon("电击魔兽");
    }
}
