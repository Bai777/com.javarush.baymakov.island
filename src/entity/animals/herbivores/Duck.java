package entity.animals.herbivores;

import entity.Herbivore;

public class Duck extends Herbivore {
    public Duck(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("duck", weight, maxCountInCell, speed, foodNeeded);
    }
}
