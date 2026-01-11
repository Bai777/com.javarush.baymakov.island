package entity.animals.predators;

import config.Settings;
import entity.Predator;

public class Wolf extends Predator {
    public Wolf() {
        super(Settings.AnimalParams.WOLF_WEIGHT,
                Settings.AnimalParams.WOLF_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.WOLF_SPEED,
                Settings.AnimalParams.WOLF_FOOD_NEEDED);
    }
}
