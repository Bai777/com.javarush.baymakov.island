package entity.animals.herbivores;

import entity.Herbivore;

public class Sheep extends Herbivore {
    public Sheep() {
        super(Settings.AnimalParams.SHEEP_WEIGHT,
                Settings.AnimalParams.SHEEP_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.SHEEP_SPEED,
                Settings.AnimalParams.SHEEP_FOOD_NEEDED);
    }
}
