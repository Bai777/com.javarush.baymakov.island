package entity.animals.predators;

import entity.Predator;

public class Boa extends Predator {
    public Boa(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("boa", weight, maxCountInCell, speed, foodNeeded);
    }
}
