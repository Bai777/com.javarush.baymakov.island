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
}
