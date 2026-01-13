package entity.animals.predators;

import entity.Predator;

public class Eagle extends Predator {
    public Eagle(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("eagle", weight, maxCountInCell, speed, foodNeeded);
    }
}
