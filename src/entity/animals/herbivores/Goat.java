package entity.animals.herbivores;

import entity.Herbivore;

public class Goat extends Herbivore {
    public Goat(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("goat", weight, maxCountInCell, speed, foodNeeded);
    }
}
