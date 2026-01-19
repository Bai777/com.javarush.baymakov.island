package entity;

import config.Config;
import config.EatingProbability;
import entity.plants.Plant;
import factory.EntityFactory;
import logic.Location;

import java.util.List;

public abstract class Herbivore extends Animal {
    Config.PlantsConfig plantsConfig;

    protected Herbivore(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
        super(animalType, weight, maxCountInCell, speed, foodNeeded);
        this.plantsConfig = Config.getInstance().getConfig().getPlants();
    }

    @Override
    public void eat(Location currentLocation) {
        if (!isAlive()) return;

        boolean canEatAnimals = EatingProbability.hasAnimalPrey(this.getClass());

        if (canEatAnimals) {
            boolean hasEatenAnimal = tryEatAnimals(currentLocation);
            if (hasEatenAnimal) {
                return;
            }
        }

        List<Plant> plants = currentLocation.getPlants();
        if (!plants.isEmpty()) {
            int plantIndex = getRandom().nextInt(plants.size());
            Plant plant = plants.get(plantIndex);
            double foodValue = plant.getFoodValue();

            if (currentLocation.removePlants(1)) {
                increaseSatiety(foodValue);
                return;
            }
        }

        decreaseSatiety(0.3);
    }

    protected boolean tryEatAnimals(Location location) {
        if (!EatingProbability.hasAnimalPrey(this.getClass())) {
            return false;
        }

        List<Animal> animals = location.getAnimalObjects();
        for (Animal prey : animals) {
            if (prey == null || !prey.isAlive() || prey == this) {
                continue;
            }

            int probability = EatingProbability.getProbability(this.getClass(), prey.getClass());

            if (probability > 0 && getRandom().nextInt(100) < probability) {
                if (location.removeAnimal(prey)) {
                    increaseSatiety(prey.getFoodValue());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void move(Location currentLocation, List<Location> adjacentLocations) {
        if (!isAlive() || getSpeed() == 0) return;

        if (getRandom().nextInt(100) < 40) {
            if (!adjacentLocations.isEmpty()) {
                Location targetLocation = adjacentLocations.get(getRandom().nextInt(adjacentLocations.size()));

                if (currentLocation != targetLocation &&
                        getCountInLocation(targetLocation) < getMaxCountInCell()) {

                    if (currentLocation.removeAnimal(this)) {
                        targetLocation.addAnimal(this);
                        decreaseSatiety(0.15);
                    }
                }
            }
        }
    }

    @Override
    public void multiply(Location currentLocation) {
        if (!isAlive()) return;

        if (currentSatiety < getFoodNeededForSaturation() * 0.5) {
            return;
        }

        int sameTypeCount = getCountInLocation(currentLocation);
        if (sameTypeCount >= 2 && getRandom().nextInt(100) < 20) {
            if (sameTypeCount < getMaxCountInCell()) {

                List<Animal> babies = multiplyWithFactory(currentLocation, EntityFactory.getInstance());
                int addedCount = 0;
                for (Animal baby : babies) {
                    if (baby != null && currentLocation.addAnimal(baby)) {
                        addedCount++;
                    }
                }

                if (addedCount > 0) {
                    decreaseSatiety(2.0 * addedCount);
                    System.out.println("[Размножение] " + getAnimalType() + ": родилось " + addedCount + " детенышей");
                }
            }
        }
    }
}
