package entity.animals.herbivores;

import entity.Herbivore;

public class Horse extends Herbivore {
    public Horse() {
        super(Settings.AnimalParams.HORSE_WEIGHT,
                Settings.AnimalParams.HORSE_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.HORSE_SPEED,
                Settings.AnimalParams.HORSE_FOOD_NEEDED);
    }
}
