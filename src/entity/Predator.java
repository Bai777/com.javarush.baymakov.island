package entity;

import config.EatingProbability;
import factory.EntityFactory;
import logic.Location;

import java.util.Collections;
import java.util.List;

public abstract class Predator extends Animal {
    protected Predator(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
        super(animalType, weight, maxCountInCell, speed, foodNeeded);
    }

    @Override
    public void eat(Location currentLocation) {
        if (!isAlive()) return;

        boolean hasEaten = tryEatAnimals(currentLocation);

        if (!hasEaten) {
            decreaseSatiety(0.3);
        }
    }

    protected boolean tryEatAnimals(Location location) {
        List<Animal> animals = location.getAnimalObjects();
        boolean isVeryHungry = currentSatiety < foodNeeded * 0.3;

        for (Animal prey : animals) {
            if (prey == null || !prey.isAlive() || prey.getAnimalType().equals(getAnimalType())) {
                continue;
            }

            if (prey.getWeight() > this.weight * 2) {
                continue;
            }

            int probability = EatingProbability.getProbability(this.getClass(), prey.getClass());
            int adjustedProbability = isVeryHungry ? Math.min(100, probability + 30) : probability;

            if (adjustedProbability > 0 && getRandom().nextInt(100) < adjustedProbability) {
                if (location.removeAnimal(prey)) {
                    increaseSatiety(prey.getWeight());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void move(Location currentLocation, List<Location> adjacentLocations) {
        if (!isAlive() || getSpeed() == 0) return;

        if (getRandom().nextInt(100) < 20) {
            if (!adjacentLocations.isEmpty()) {
                Location targetLocation = adjacentLocations.get(getRandom().nextInt(adjacentLocations.size()));

                if (currentLocation != targetLocation &&
                        getCountInLocation(targetLocation) < getMaxCountInCell()) {

                    if (currentLocation.removeAnimal(this)) {
                        targetLocation.addAnimal(this);
                        decreaseSatiety(0.3);
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
        if (sameTypeCount >= 2 && getRandom().nextInt(100) < 30) {
            if (sameTypeCount < getMaxCountInCell()) {
                List<Animal> babies = multiplyWithFactory(currentLocation, EntityFactory.getInstance());
                int addedCount = 0;
                for (Animal baby : babies) {
                    if (baby != null && currentLocation.addAnimal(baby)) {
                        addedCount++;
                    }
                }
                if (addedCount > 0) {
                    decreaseSatiety(3.0 * addedCount);
                    //Для отладки
                    System.out.println("[Размножение] " + getAnimalType() + ": родилось " + addedCount + " детенышей");
                }
            }
        }
    }

    @Override
    public void die() {
        super.die();
    }
}
