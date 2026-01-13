package entity.animals.herbivores;

import entity.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("caterpillar", weight, maxCountInCell, speed, foodNeeded);
    }
}
