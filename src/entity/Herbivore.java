package entity;

import config.Config;
import entity.plants.Plant;
import logic.Location;

import java.util.List;

public abstract class Herbivore extends Animal {
    Config.PlantsConfig plantsConfig;
    protected Herbivore(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
        super(animalType, weight, maxCountInCell, speed, foodNeeded);
        this.plantsConfig = new Config.PlantsConfig();
    }

    @Override
    public void eat(Location currentLocation) {
        if (!isAlive()) return;

        int plantsCount = currentLocation.getPlantsCount();
        if (plantsCount > 0) {
            double maxCanEat = getFoodNeededForSaturation() * 0.1;
            double actualEat = Math.min(maxCanEat, plantsCount * plantsConfig.getWeight());

            if (actualEat > 0) {
                int plantsToRemove = (int) Math.ceil(actualEat / plantsConfig.getWeight());
                currentLocation.removePlants(Math.min(plantsToRemove, plantsCount));
                increaseSatiety(actualEat);
                return;
            }
        }

        decreaseSatiety(0.3);
    }

    @Override
    public boolean canEat(Eatable food) {
        return food instanceof Plant;
    }

    @Override
    public int getEatingProbability(Class<? extends Animal> preyClass) {
        return 0;
    }

    @Override
    public void move(Location currentLocation, List<Location> adjacentLocations) {
        if (!isAlive() || getSpeed() == 0) return;

        if (getRandom().nextInt(100) < 40) {
            if (!adjacentLocations.isEmpty()) {
                Location targetLocation = adjacentLocations.get(getRandom().nextInt(adjacentLocations.size()));

                int currentCount = currentLocation.getAnimalCount(this.getClass());
                if (currentLocation != targetLocation &&
                        targetLocation.getAnimalCount(this.getClass()) < getMaxCountInCell()) {

                    currentLocation.removeAnimal(this.getClass());
                    targetLocation.addAnimal(this);
                    decreaseSatiety(0.15);
                }
            }
        }
    }

    @Override
    public void multiply(Location currentLocation) {
        if (!isAlive()) return;

        int sameTypeCount = currentLocation.getAnimalCount(this.getClass());
        if (sameTypeCount >= 2 && getRandom().nextInt(100) < 20) {
            if (sameTypeCount < getMaxCountInCell()) {
                try {
                    Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                    currentLocation.addAnimal(baby);
                    decreaseSatiety(0.8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
