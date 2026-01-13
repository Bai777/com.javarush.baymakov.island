package entity.animals.herbivores;

import entity.Herbivore;

public class Deer extends Herbivore {
    public Deer() {
        super(Settings.AnimalParams.DEER_WEIGHT,
                Settings.AnimalParams.DEER_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.DEER_SPEED,
                Settings.AnimalParams.DEER_FOOD_NEEDED);
    }
}
