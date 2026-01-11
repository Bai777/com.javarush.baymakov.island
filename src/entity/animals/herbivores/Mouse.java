package entity.animals.herbivores;

import config.Settings;
import entity.Herbivore;

public class Mouse extends Herbivore {
    public Mouse() {
        super(Settings.AnimalParams.MOUSE_WEIGHT,
                Settings.AnimalParams.MOUSE_MAX_COUNT_IN_CELL,
                Settings.AnimalParams.MOUSE_SPEED,
                Settings.AnimalParams.MOUSE_FOOD_NEEDED);
    }
}
