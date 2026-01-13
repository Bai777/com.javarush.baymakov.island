package entity.animals.predators;

import entity.Predator;

public class Wolf extends Predator {
    public Wolf(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("wolf", weight, maxCountInCell, speed, foodNeeded);
    }
}
