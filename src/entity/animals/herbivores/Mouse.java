package entity.animals.herbivores;

import config.Settings;
import entity.Eatable;
import entity.Herbivore;

public class Mouse extends Herbivore {
    public Mouse() {
        super(Settings.AnimalParams.MOUSE_WEIGHT,
                Settings.AnimalParams.MOUSE_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.MOUSE_SPEED,
                Settings.AnimalParams.MOUSE_FOOD_NEEDED);
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
