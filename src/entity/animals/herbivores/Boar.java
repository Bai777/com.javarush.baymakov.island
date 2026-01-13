package entity.animals.herbivores;

import entity.Herbivore;

public class Boar extends Herbivore {
    public Boar() {
        super(Settings.AnimalParams.BOAR_WEIGHT,
                Settings.AnimalParams.BOAR_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.BOAR_SPEED,
                Settings.AnimalParams.BOAR_FOOD_NEEDED);
    }
}
