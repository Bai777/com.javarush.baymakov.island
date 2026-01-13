package entity.animals.herbivores;

import entity.Herbivore;

public class Deer extends Herbivore {
    public Deer(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("deer", weight, maxCountInCell, speed, foodNeeded);
    }
}
