package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Deer extends Herbivore {
    public Deer() {
        super(Settings.AnimalParams.DEER_WEIGHT,
                Settings.AnimalParams.DEER_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.DEER_SPEED,
                Settings.AnimalParams.DEER_FOOD_NEEDED);
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
