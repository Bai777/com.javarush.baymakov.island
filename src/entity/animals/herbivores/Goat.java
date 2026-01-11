package entity.animals.herbivores;

import config.Settings;
import entity.Herbivore;

public class Goat extends Herbivore {
    public Goat() {
        super(Settings.AnimalParams.GOAT_WEIGHT,
                Settings.AnimalParams.GOAT_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.GOAT_SPEED,
                Settings.AnimalParams.GOAT_FOOD_NEEDED);
    }
}
