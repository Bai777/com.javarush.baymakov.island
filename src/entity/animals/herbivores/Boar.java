package entity.animals.herbivores;

import entity.Omnivore;

public class Boar extends Omnivore {
    public Boar(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("boar", weight, maxCountInCell, speed, foodNeeded);
    }
}
