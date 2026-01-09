package entity.animals.predators;

import config.Settings;
import entity.Eatable;
import entity.Predator;

public class Boa extends Predator {
    public Boa() {
        super(Settings.AnimalParams.BOA_WEIGHT,
                Settings.AnimalParams.BOA_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.BOA_SPEED,
                Settings.AnimalParams.BOA_FOOD_NEEDED);
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
