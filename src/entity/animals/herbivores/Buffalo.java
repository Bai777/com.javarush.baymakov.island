package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(Settings.AnimalParams.BUFFALO_WEIGHT,
                Settings.AnimalParams.BUFFALO_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.BUFFALO_SPEED,
                Settings.AnimalParams.BUFFALO_FOOD_NEEDED);
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
