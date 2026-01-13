package entity.animals.herbivores;

import entity.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit() {
        super(Settings.AnimalParams.RABBIT_WEIGHT,
                Settings.AnimalParams.RABBIT_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.RABBIT_SPEED,
                Settings.AnimalParams.RABBIT_FOOD_NEEDED);
    }
}
