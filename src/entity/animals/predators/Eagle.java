package entity.animals.predators;

import config.Settings;
import entity.Eatable;
import entity.Predator;

public class Eagle extends Predator {
    public Eagle() {
        super(Settings.AnimalParams.EAGLE_WEIGHT,
                Settings.AnimalParams.EAGLE_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.EAGLE_SPEED,
                Settings.AnimalParams.EAGLE_FOOD_NEEDED);
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
