package entity.animals.herbivores;

import entity.Omnivore;

public class Duck extends Omnivore {
    public Duck(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("duck", weight, maxCountInCell, speed, foodNeeded);
    }
}
