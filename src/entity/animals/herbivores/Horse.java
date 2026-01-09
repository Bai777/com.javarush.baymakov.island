package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Horse extends Herbivore {
    public Horse() {
        super(Settings.AnimalParams.HORSE_WEIGHT,
                Settings.AnimalParams.HORSE_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.HORSE_SPEED,
                Settings.AnimalParams.HORSE_FOOD_NEEDED);
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
