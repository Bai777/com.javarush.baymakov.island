package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(Settings.AnimalParams.CATERPILLAR_WEIGHT,
                Settings.AnimalParams.CATERPILLAR_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.CATERPILLAR_SPEED,
                Settings.AnimalParams.CATERPILLAR_FOOD_NEEDED);
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
