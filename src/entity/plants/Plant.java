package entity.plants;

import config.Settings;
import entity.Eatable;

public class Plant implements Eatable {
    private double weight;

    public Plant() {
        this.weight = Settings.PLANT_WEIGHT;
    }

    public double getWeight() {
        return weight;
    }
}
