package entity.animals.herbivores;

import entity.Omnivore;

public class Mouse extends Omnivore {
    public Mouse(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("mouse", weight, maxCountInCell, speed, foodNeeded);
    }
}
