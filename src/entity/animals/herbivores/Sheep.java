package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Sheep extends Herbivore {
    public Sheep() {
        super(Settings.AnimalParams.SHEEP_WEIGHT,
                Settings.AnimalParams.SHEEP_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.SHEEP_SPEED,
                Settings.AnimalParams.SHEEP_FOOD_NEEDED);
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
