package creatingPatterns.builder;

/**
 * Product
 * 一个多属性队伍由多个属性的宝可梦组成
 * 省略了Pokemon接口，直接用String
 */
public class MultiTypeTeam {
    private String fireType; // 火属性
    private String waterType; // 水属性
    private String flyingType; // 飞行系
    private String grassType; // 草属性
    private String electricType; // 电属性

    public void setFireType(String fireType) {
        this.fireType = fireType;
    }

    public void setWaterType(String waterType) {
        this.waterType = waterType;
    }

    public void setFlyingType(String flyingType) {
        this.flyingType = flyingType;
    }

    public void setGrassType(String grassType) {
        this.grassType = grassType;
    }

    public void setElectricType(String electricType) {
        this.electricType = electricType;
    }

    @Override
    public String toString() {
        return "MultiTypeTeam{" +
                "fireType='" + fireType + '\'' +
                ", waterType='" + waterType + '\'' +
                ", flyingType='" + flyingType + '\'' +
                ", grassType='" + grassType + '\'' +
                ", electricType='" + electricType + '\'' +
                '}';
    }
    // ...
}
