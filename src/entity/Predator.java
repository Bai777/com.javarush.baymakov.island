package entity;

import config.EatingProbability;
import factory.EntityFactory;
import logic.Location;

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

        for (Animal prey : animals) {
            if (!prey.isAlive() || prey.getAnimalType().equals(getAnimalType())) {
                continue;
            }

            int probability = getEatingProbability(prey.getClass());

            if (probability > 0 && getRandom().nextInt(100) < probability) {
                if (location.removeAnimal(prey)) {
                    increaseSatiety(prey.getWeight());
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int getEatingProbability(Class<? extends Animal> preyClass) {
        return EatingProbability.getProbability(this.getClass(), preyClass);
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

        int sameTypeCount = getCountInLocation(currentLocation);
        if (sameTypeCount >= 2 && getRandom().nextInt(100) < 30) {
            if (sameTypeCount < getMaxCountInCell()) {
                try {
                    Animal baby = multiplyWithFactory(currentLocation, EntityFactory.getInstance());
                    if (baby != null && currentLocation.addAnimal(baby)) {
                        decreaseSatiety(1.5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void die() {
        super.die();
    }
}
