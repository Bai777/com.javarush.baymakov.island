package entity;

public abstract class Herbivore extends Animal{
    public Herbivore(double weight, int maxCountInCell, int speed, double foodNeededForSaturation) {
        super(weight, maxCountInCell, speed, foodNeededForSaturation);
    }
}
