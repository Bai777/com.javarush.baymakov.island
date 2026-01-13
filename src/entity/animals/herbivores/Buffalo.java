package entity.animals.herbivores;

import entity.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo() {
        super(Settings.AnimalParams.BUFFALO_WEIGHT,
                Settings.AnimalParams.BUFFALO_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.BUFFALO_SPEED,
                Settings.AnimalParams.BUFFALO_FOOD_NEEDED);
    }
}
