package entity.animals.herbivores;

import entity.Herbivore;

public class Boar extends Herbivore {
    public Boar(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("boar", weight, maxCountInCell, speed, foodNeeded);
    }
}
