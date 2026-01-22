package entity;

import config.Config;
import config.Constants;
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
        if (tryEatPlants(currentLocation)) {
            return;
        }
        decreaseSatiety(Constants.ForHerbivore.decreaseSatiety);
    }

    @Override
    public void move(Location currentLocation, List<Location> adjacentLocations) {
        commonMove(currentLocation,
                adjacentLocations,
                Constants.ForHerbivore.moveProbability,
                Constants.ForHerbivore.satietyDecrease);
    }

    @Override
    public void multiply(Location currentLocation) {
        commonMultiply(currentLocation,
                Constants.ForHerbivore.reproductionProbability,
                Constants.ForHerbivore.satietyDecreasePerBaby);
    }
}
