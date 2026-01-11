package entity.animals.herbivores;

import config.Settings;
import entity.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(Settings.AnimalParams.CATERPILLAR_WEIGHT,
                Settings.AnimalParams.CATERPILLAR_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.CATERPILLAR_SPEED,
                Settings.AnimalParams.CATERPILLAR_FOOD_NEEDED);
    }
}
