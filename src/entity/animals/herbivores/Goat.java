package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Goat extends Herbivore {
    public Goat() {
        super(Settings.AnimalParams.GOAT_WEIGHT,
                Settings.AnimalParams.GOAT_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.GOAT_SPEED,
                Settings.AnimalParams.GOAT_FOOD_NEEDED);
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
