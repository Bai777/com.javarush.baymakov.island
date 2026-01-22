package entity;

import config.Config;
import config.Constants;
import config.EatingProbability;
import logic.Location;

import java.util.List;

public abstract class Omnivore extends Animal {
    Config.PlantsConfig plantsConfig;

    protected Omnivore(String animalType, double weight, int maxCountInCell, int speed, double foodNeeded) {
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
        if (tryEatPlants(currentLocation)) {
            return;
        }
        decreaseSatiety(Constants.ForOmnivore.decreaseSatiety);
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

            Integer probability = EatingProbability.getProbability(this.getClass(), prey.getClass());

            if (probability > 0 && getRandom().nextInt(100) < probability) {
                if (location.removeAnimal(prey)) {
                    increaseSatiety(prey.getFoodValue());
                    prey.die();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void move(Location currentLocation, List<Location> adjacentLocations) {
        commonMove(currentLocation,
                adjacentLocations,
                Constants.ForOmnivore.moveProbability, Constants.ForOmnivore.satietyDecrease);
    }

    @Override
    public void multiply(Location currentLocation) {
        commonMultiply(currentLocation,
                Constants.ForOmnivore.reproductionProbability,
                Constants.ForOmnivore.satietyDecreasePerBaby);
    }
}
