package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(Settings.AnimalParams.RABBIT_WEIGHT,
                Settings.AnimalParams.RABBIT_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.RABBIT_SPEED,
                Settings.AnimalParams.RABBIT_FOOD_NEEDED);
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
