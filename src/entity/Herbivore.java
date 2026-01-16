package entity;

import config.Config;
import factory.EntityFactory;
import logic.Location;

import java.util.Collections;
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

        int plantsCount = currentLocation.getPlantsCount();
        if (plantsCount > 0) {
            double maxCanEat = getFoodNeededForSaturation() * 0.3;
            double actualEat = Math.min(maxCanEat, plantsCount * plantsConfig.getWeight());

            if (actualEat > 0) {
                int plantsToRemove = (int) Math.ceil(actualEat / plantsConfig.getWeight());
                if (currentLocation.removePlants(Math.min(plantsToRemove, plantsCount))) {
                    increaseSatiety(actualEat);
                    return;
                }
            }
        }

        decreaseSatiety(0.3);
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

        int sameTypeCount = getCountInLocation(currentLocation);
        if (sameTypeCount >= 2 && getRandom().nextInt(100) < 20) {
            if (sameTypeCount < getMaxCountInCell()) {

                List<Animal> babies = Collections.singletonList(multiplyWithFactory(currentLocation, EntityFactory.getInstance()));
                int addedCount = 0;
                for (Animal baby : babies) {
                    if (currentLocation.addAnimal(baby)) {
                        addedCount++;
                    }
                }

                if (addedCount > 0) {
                    decreaseSatiety(0.8);
                }
            }
        }
    }
}
