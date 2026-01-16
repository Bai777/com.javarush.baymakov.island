package entity;

import config.Config;
import factory.EntityFactory;
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
    private final Random random = new Random();

    protected Animal(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
        this.animalType = animalType;
        this.weight = weight;
        this.maxCountInCell = maxCountInCell;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
        this.currentSatiety = foodNeeded / 2;
    }

    public String getAnimalType() {
        return animalType;
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

    public boolean isAlive() {
        return isAlive;
    }

    public Random getRandom() {
        return random;
    }

    protected int getCountInLocation(Location location) {
        return location.getAnimalCount(getAnimalType());
    }

    public void setCurrentSatiety(double currentSatiety) {
        this.currentSatiety = Math.min(currentSatiety, foodNeeded);
    }

    public void eat(Location currentLocation) {
        if (!isAlive) return;

        decreaseSatiety(Config.getInstance().getConfig().getSimulation().getSatietyDecreasePerTick());
    }

    public void move(Location currentLocation,
                     List<Location> adjacentLocations) {
        if (!isAlive || speed == 0) return;

        if (random.nextInt(100) < 50 && !adjacentLocations.isEmpty()) {
            Location targetLocation = adjacentLocations.get(random.nextInt(adjacentLocations.size()));

            if (currentLocation != targetLocation &&
                    targetLocation.getAnimalCount(this.getAnimalType()) < maxCountInCell) {
                if (currentLocation.removeAnimal(this)) {
                    targetLocation.addAnimal(this);
                    decreaseSatiety(0.1);
                }
            }
        }
    }

    public void multiply(Location currentLocation) {
    }

    public Animal multiplyWithFactory(Location currentLocation, EntityFactory factory) {
        List<Animal> babies = new ArrayList<>();

        if (!isAlive) return (Animal) babies;

        int sameTypeCount = currentLocation.getAnimalCount(this.getAnimalType());
        Config.ReproductionConfig reprodConfig = Config.getInstance().getConfig().getReproduction();

        if (sameTypeCount >= reprodConfig.getMinAnimalsForReproduction()) {

            if (random.nextDouble() < reprodConfig.getReproductionProbability() &&
                    sameTypeCount < maxCountInCell) {

                int maxNewborns = reprodConfig.getMaxNewbornsPerPair();
                int availableSpace = maxCountInCell - sameTypeCount;
                int newborns = Math.min(maxNewborns, availableSpace);

                if (newborns > 0) {
                    for (int i = 0; i < newborns; i++) {
                        try {
                            Animal baby = factory.createBabyAnimal(this.getAnimalType());
                            if (baby != null) {
                                baby.setCurrentSatiety(baby.getFoodNeededForSaturation() / 2);
                                babies.add(baby);
                            }
                        } catch (Exception e) {
                            System.err.println("Ошибка создания детеныша " + getAnimalType() + ": " + e.getMessage());
                        }
                    }
                    this.decreaseSatiety(1.0 * newborns);
                }
            }
        }
        return null;
    }

    public void die() {
        this.isAlive = false;
    }

    public void decreaseSatiety(double amount) {
        currentSatiety = Math.max(0, currentSatiety - amount);
        if (currentSatiety <= 0) {
            die();
        }
    }

    public void increaseSatiety(double amount) {
        currentSatiety = Math.min(currentSatiety + amount, foodNeeded);
    }

}
