package entity.animals.herbivores;

import entity.Herbivore;

public class Rabbit extends Herbivore {
    public Rabbit(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("rabbit", weight, maxCountInCell, speed, foodNeeded);
    }
}
