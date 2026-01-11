package entity;

import logic.Location;

import java.util.List;
import java.util.Random;

public abstract class Animal implements Eatable {
    private double weight;
    private int maxCountInCell;
    private int speed;
    private double foodNeededForSaturation;
    private double currentSatiety;
    private boolean isAlive;
    private Random random;

    public Animal(double weight, int maxCountInCell,
                  int speed, double foodNeededForSaturation) {
        this.weight = weight;
        this.maxCountInCell = maxCountInCell;
        this.speed = speed;
        this.foodNeededForSaturation = foodNeededForSaturation;
        this.currentSatiety = foodNeededForSaturation;
        this.isAlive = true;
        this.random = new Random();
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

    public double getCurrentSatiety() {
        return currentSatiety;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Random getRandom() {
        return random;
    }

    public void setCurrentSatiety(double satiety) {
        this.currentSatiety = Math.min(satiety, foodNeededForSaturation);
    }

    public void eat(Location currentLocation) {
        decreaseSatiety(0.5);
    }

    public void move(Location currentLocation, List<Location> adjacentLocations) {
        if (!isAlive || speed == 0) return;

        if (random.nextInt(100) < 30) {
            if (!adjacentLocations.isEmpty()) {
                Location targetLocation = adjacentLocations.get(random.nextInt(adjacentLocations.size()));

                int currentCount = currentLocation.getAnimalCount(this.getClass());
                if (currentLocation != targetLocation &&
                        targetLocation.getAnimalCount(this.getClass()) < maxCountInCell) {
                    currentLocation.removeAnimal(this.getClass());
                    targetLocation.addAnimal(this);
                    decreaseSatiety(0.2);
                }
            }
        }
    }

    public void multiply(Location currentLocation) {
        if (!isAlive) return;

        int sameTypeCount = currentLocation.getAnimalCount(this.getClass());
        if (sameTypeCount >= 2 && random.nextInt(100) < 15) {
            if (sameTypeCount < maxCountInCell) {
                try {
                    Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                    currentLocation.addAnimal(baby);
                    decreaseSatiety(1.0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void die() {
        this.isAlive = false;
    }

    public void decreaseSatiety(double amount) {
        currentSatiety -= amount;
        if (currentSatiety <= 0) {
            die();
        }
    }

    public void increaseSatiety(double amount) {
        currentSatiety = Math.min(currentSatiety + amount, foodNeededForSaturation);
    }

    public boolean canEat(Eatable food) {
        return false;
    }

    public int getEatingProbability(Class<? extends Animal> preyClass) {
        return 0;
    }
}
