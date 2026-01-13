package entity;

import config.EatingProbability;
import logic.Location;

import java.util.List;
import java.util.Map;

public abstract class Predator extends Animal {
    protected Predator(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
        super(animalType, weight, maxCountInCell, speed, foodNeeded);
    }

    @Override
    public void eat(Location currentLocation) {
        if (!isAlive()) return;

        boolean hasEaten = tryEatAnimals(currentLocation);

        if (!hasEaten) {
            decreaseSatiety(0.5);
        }
    }

    protected boolean tryEatAnimals(Location location) {
        Map<Class<? extends Animal>, Integer> animals = location.getAnimals();

        for (Map.Entry<Class<? extends Animal>, Integer> entry : animals.entrySet()) {
            Class<? extends Animal> preyClass = entry.getKey();
            int count = entry.getValue();

            if (preyClass.equals(this.getClass())) {
                continue;
            }

            int probability = getEatingProbability(preyClass);

            if (probability > 0 && count > 0 && getRandom().nextInt(100) < probability) {

                if (location.removeAnimal(preyClass).isAlive()) {
                    try {
                        Animal sample = preyClass.getDeclaredConstructor().newInstance();
                        increaseSatiety(sample.getWeight());
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    public boolean canEat(Eatable food) {
        if (food instanceof Animal) {
            Class<? extends Animal> foodClass = ((Animal) food).getClass();
            return EatingProbability.getProbability(this.getClass(), foodClass) > 0;
        }
        return false;
    }

    @Override
    public void move(Location currentLocation, List<Location> adjacentLocations) {
        if (!isAlive() || getSpeed() == 0) return;

        if (getRandom().nextInt(100) < 20) {
            if (!adjacentLocations.isEmpty()) {
                Location targetLocation = adjacentLocations.get(getRandom().nextInt(adjacentLocations.size()));

                int currentCount = currentLocation.getAnimalCount(this.getClass());
                if (currentLocation != targetLocation &&
                        targetLocation.getAnimalCount(this.getClass()) < getMaxCountInCell()) {

                    currentLocation.removeAnimal(this.getClass());
                    targetLocation.addAnimal(this);
                    decreaseSatiety(0.3);
                }
            }
        }
    }

    @Override
    public void multiply(Location currentLocation) {
        if (!isAlive()) return;

        int sameTypeCount = currentLocation.getAnimalCount(this.getClass());
        if (sameTypeCount >= 2 && getRandom().nextInt(100) < 10) {
            if (sameTypeCount < getMaxCountInCell()) {
                try {
                    Animal baby = this.getClass().getDeclaredConstructor().newInstance();
                    currentLocation.addAnimal(baby);
                    decreaseSatiety(1.5);
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
