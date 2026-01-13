package entity.animals.predators;

import entity.Predator;

public class Bear extends Predator {
    public Bear(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("bear", weight, maxCountInCell, speed, foodNeeded);
    }
}
