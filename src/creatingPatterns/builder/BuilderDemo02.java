package creatingPatterns.builder;

public class BuilderDemo02 {
    public static void main(String[] args) {
        MultiTypeTeam satoshiTeam = new ConcreteTeamBuilderSimple("小锯鳄")
                .chooseElectricType("皮卡丘")
                .chooseFireType("暖暖猪")
                .chooseFlyingType("急冻鸟")
                .chooseGrassType("菊草叶")
                .getTeam();

        MultiTypeTeam mistyTeam = new ConcreteTeamBuilderSimple("暴鲤龙")
                .getTeam();

        System.out.println(satoshiTeam.toString());
        System.out.println(mistyTeam.toString());
    }
}
