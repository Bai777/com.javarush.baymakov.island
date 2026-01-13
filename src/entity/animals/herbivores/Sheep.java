package entity.animals.herbivores;

import entity.Herbivore;

public class Sheep extends Herbivore {
    public Sheep(double weight, int maxCountInCell, int speed, double foodNeeded) {
        super("sheep", weight, maxCountInCell, speed, foodNeeded);
    }
}
