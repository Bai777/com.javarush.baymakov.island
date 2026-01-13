package entity.animals.predators;

import entity.Predator;

public class Bear extends Predator {
    public Bear() {
        super(Settings.AnimalParams.BEAR_WEIGHT,
                Settings.AnimalParams.BEAR_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.BEAR_SPEED,
                Settings.AnimalParams.BEAR_FOOD_NEEDED);
    }
}
