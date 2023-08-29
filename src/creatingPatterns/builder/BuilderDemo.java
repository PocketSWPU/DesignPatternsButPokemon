package creatingPatterns.builder;

public class BuilderDemo {
    public static void main(String[] args) {
        Director director = new Director();
        MultiTypeTeam satoshiTeam = director.buildTeam(new SatoshiTeamBuilder());
        MultiTypeTeam mistyTeam = director.buildTeam(new MistyTeamBuilder());

        System.out.println(satoshiTeam.toString());
        System.out.println(mistyTeam.toString());
    }
}
