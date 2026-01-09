package entity;

public abstract class Predator extends Animal{
    public Predator(double weight, int maxCountInCell, int speed, double foodNeededForSaturation) {
        super(weight, maxCountInCell, speed, foodNeededForSaturation);
    }
}
