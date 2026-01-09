package entity;

public abstract class Animal implements Eatable {
    private double weight;
    private int maxCountInCell;
    private int speed;
    private double foodNeededForSaturation;

    public Animal(double weight, int maxCountInCell, int speed, double foodNeededForSaturation) {
        this.weight = weight;
        this.maxCountInCell = maxCountInCell;
        this.speed = speed;
        this.foodNeededForSaturation = foodNeededForSaturation;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxCountInCell() {
        return maxCountInCell;
    }

    public int getSpeed() {
        return speed;
    }

    public double getFoodNeededForSaturation() {
        return foodNeededForSaturation;
    }

    public abstract void eat(Eatable food);
    public abstract void move();
    public abstract void multiply();
    public abstract void die();
}
