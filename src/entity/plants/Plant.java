package entity.plants;

import entity.Eatable;

public class Plant implements Eatable {
    private double weight;

    public Plant(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public double getFoodValue() {
        return weight;
    }
}
