package entity.animals.predators;

import config.Settings;
import entity.Eatable;
import entity.Predator;

public class Fox extends Predator {
    public Fox() {
        super(Settings.AnimalParams.FOX_WEIGHT,
                Settings.AnimalParams.FOX_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.FOX_SPEED,
                Settings.AnimalParams.FOX_FOOD_NEEDED);
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
