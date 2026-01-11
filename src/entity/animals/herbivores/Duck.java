package entity.animals.herbivores;

import config.Settings;
import entity.Herbivore;

public class Duck extends Herbivore {
    public Duck() {
        super(Settings.AnimalParams.DUCK_WEIGHT,
                Settings.AnimalParams.DUCK_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.DUCK_SPEED,
                Settings.AnimalParams.DUCK_FOOD_NEEDED);
    }
}
