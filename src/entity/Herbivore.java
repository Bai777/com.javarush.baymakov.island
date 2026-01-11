package entity;

import config.Settings;
import entity.plants.Plant;
import logic.Location;

import java.util.List;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight, int maxCountInCell, int speed, double foodNeededForSaturation) {
        super(weight, maxCountInCell, speed, foodNeededForSaturation);
    }

    @Override
    public void eat(Location currentLocation) {
        if (!isAlive()) return;

        int plantsCount = currentLocation.getPlantsCount();
        if (plantsCount > 0) {
            double maxCanEat = getFoodNeededForSaturation() * 0.1;
            double actualEat = Math.min(maxCanEat, plantsCount * Settings.PLANT_WEIGHT);

            if (actualEat > 0) {
                int plantsToRemove = (int) Math.ceil(actualEat / Settings.PLANT_WEIGHT);
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
