package entity.animals.predators;

import config.Settings;
import entity.Eatable;
import entity.Predator;

public class Bear extends Predator {
    public Bear() {
        super(Settings.AnimalParams.BEAR_WEIGHT,
                Settings.AnimalParams.BEAR_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.BEAR_SPEED,
                Settings.AnimalParams.BEAR_FOOD_NEEDED);
    }

    @Override
    public void eat(Eatable food) {
        // TODO:
    }

    @Override
    public void move() {
        // TODO:
    }

    @Override
    public void multiply() {
        // TODO:
    }

    @Override
    public void die() {
        // TODO:
    }
}
