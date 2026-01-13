package entity;

import config.Config;
import logic.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Animal {
    protected final double weight;
    protected final int maxCountInCell;
    protected final int speed;
    protected final double foodNeeded;
    protected double currentSatiety;
    protected boolean isAlive = true;
    protected final String animalType;

    protected Animal(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
        this.animalType = animalType;
        this.weight = weight;
        this.maxCountInCell = maxCountInCell;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
        this.currentSatiety = foodNeeded / 2;
    }

    protected double getHuntProbability() {
        Config.AnimalConfig cfg = Config.getInstance().getConfig().getAnimals().getAnimalConfig(animalType);
        return cfg != null ? cfg.getHuntProbability() : 0.0;
    }

    protected List<String> getPreyTypes() {
        Config.AnimalConfig cfg = Config.getInstance().getConfig().getAnimals().getAnimalConfig(animalType);
        return cfg != null ? cfg.getPreyTypes() : new ArrayList<>();
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
        return foodNeeded;
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
                    currentLocation.removeAnimal(this);
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
