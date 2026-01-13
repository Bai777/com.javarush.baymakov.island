package entity.plants;

import entity.Eatable;

public class Plant implements Eatable {
    private double weight;

    public Plant(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

//    public double eat(double amount) {
//        double eaten = Math.min(amount, weight);
//        weight -= eaten;
//        return eaten;
//    }

    public boolean isAlive() {
        return weight > 0;
    }
}
