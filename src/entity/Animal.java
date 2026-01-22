package entity;

import config.Config;
import config.Constants;
import entity.plants.Plant;
import factory.EntityFactory;
import logic.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Animal implements Eatable {
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
        this.currentSatiety = foodNeeded / Constants.ForAnimal.currentSatietyHalf;
    }

    public String getAnimalType() {
        return animalType;
    }

    @Override
    public double getFoodValue() {
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

    public double getCurrentSatiety() {
        return currentSatiety;
    }

    public void setCurrentSatiety(double currentSatiety) {
        this.currentSatiety = Math.min(currentSatiety, foodNeeded);
    }

    public void eat(Location currentLocation) {
        if (!isAlive) return;
        decreaseSatiety(Config.getInstance().getConfig().getSimulation().getSatietyDecreasePerTick());
    }

    protected boolean tryEatPlants(Location currentLocation) {
        List<Plant> plants = currentLocation.getPlants();
        if (!plants.isEmpty()) {
            int plantIndex = getRandom().nextInt(plants.size());
            Plant plant = plants.get(plantIndex);
            double foodValue = plant.getFoodValue();

            if (currentLocation.removePlants(Constants.ForAnimal.onePlant)) {
                increaseSatiety(foodValue);
                return true;
            }
        }
        return false;
    }

    protected void commonMove(Location currentLocation,
                              List<Location> adjacentLocations,
                              int moveProbability,
                              double satietyDecrease) {
        if (!isAlive() || getSpeed() == 0) return;

        if (getRandom().nextInt(100) < moveProbability) {
            if (!adjacentLocations.isEmpty()) {
                Location targetLocation = adjacentLocations.get(getRandom().nextInt(adjacentLocations.size()));

                if (currentLocation != targetLocation &&
                        getCountInLocation(targetLocation) < getMaxCountInCell()) {

                    if (currentLocation.removeAnimal(this)) {
                        targetLocation.addAnimal(this);
                        decreaseSatiety(satietyDecrease);
                    }
                }
            }
        }
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
                    decreaseSatiety(Constants.ForAnimal.decreaseSatiety);
                }
            }
        }
    }

    protected void commonMultiply(Location currentLocation,
                                  int reproductionProbability,
                                  double satietyDecreasePerBaby) {
        if (!isAlive()) return;

        if (currentSatiety < getFoodNeededForSaturation() * Constants.ForAnimal.foodNeededForSaturation) {
            return;
        }

        Integer sameTypeCount = getCountInLocation(currentLocation);
        if (sameTypeCount >= Constants.ForAnimal.sameTypeCount && getRandom().nextInt(100) < reproductionProbability) {
            if (sameTypeCount < getMaxCountInCell()) {

                List<Animal> babies = multiplyWithFactory(currentLocation, EntityFactory.getInstance());
                Integer addedCount = Constants.ForAnimal.addedCount;
                for (Animal baby : babies) {
                    if (baby != null && currentLocation.addAnimal(baby)) {
                        addedCount++;
                    }
                }

                if (addedCount > 0) {
                    decreaseSatiety(satietyDecreasePerBaby * addedCount);
                    System.out.println("[Размножение] " + getAnimalType() +
                            ": родилось " + addedCount + " детенышей");
                }
            }
        }
    }

    public void multiply(Location currentLocation) {
    }

    public List<Animal> multiplyWithFactory(Location currentLocation, EntityFactory factory) {
        List<Animal> babies = new ArrayList<>();

        if (!isAlive) return babies;

        int sameTypeCount = currentLocation.getAnimalCount(this.getAnimalType());
        Config.ReproductionConfig reproductionConfig = Config.getInstance().getConfig().getReproduction();

        if (sameTypeCount >= reproductionConfig.getMinAnimalsForReproduction()) {

            if (random.nextDouble() < reproductionConfig.getReproductionProbability() &&
                    sameTypeCount < maxCountInCell) {

                int maxNewborns = reproductionConfig.getMaxNewbornsPerPair();
                int availableSpace = maxCountInCell - sameTypeCount;
                int newborns = Math.min(maxNewborns, availableSpace);

                if (newborns > Constants.ForAnimal.newbornsZero) {
                    for (int i = 0; i < newborns; i++) {
                        try {
                            Animal baby = factory.createBabyAnimal(this.getAnimalType());
                            if (baby != null) {
                                baby.setCurrentSatiety(baby.getFoodNeededForSaturation() / Constants.ForAnimal.currentSatietyHalf);
                                babies.add(baby);
                            }
                        } catch (Exception e) {
                            System.err.println(Constants.ForAnimal.errorReproductionBaby + getAnimalType() + ": " + e.getMessage());
                        }
                    }

                    if (!babies.isEmpty()) {
                        this.decreaseSatiety(Constants.ForAnimal.energyForMultiply * babies.size());
                    }
                }
            }
        }
        return babies;
    }

    public void die() {
        this.isAlive = false;
    }

    public void decreaseSatiety(double amount) {
        currentSatiety = Math.max(0, currentSatiety - amount);
        if (currentSatiety <= Constants.ForAnimal.currentSatietyZero) {
            die();
        }
    }

    public void increaseSatiety(double amount) {
        currentSatiety = Math.min(currentSatiety + amount, foodNeeded);
    }
}
