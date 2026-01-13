package entity.animals.herbivores;

import entity.Herbivore;

public class Mouse extends Herbivore {
    public Mouse(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("mouse", weight, maxCountInCell, speed, foodNeeded);
    }
}
