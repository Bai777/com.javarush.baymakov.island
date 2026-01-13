package entity.animals.herbivores;

import entity.Herbivore;

public class Horse extends Herbivore {
    public Horse(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("horse", weight, maxCountInCell, speed, foodNeeded);
    }
}
