package entity.animals.predators;

import entity.Predator;

public class Fox extends Predator {
    public Fox(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("fox", weight, maxCountInCell, speed, foodNeeded);
    }
}
