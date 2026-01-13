package entity.animals.herbivores;

import entity.Herbivore;

public class Buffalo extends Herbivore {
    public Buffalo(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("buffalo", weight, maxCountInCell, speed, foodNeeded);
    }
}
