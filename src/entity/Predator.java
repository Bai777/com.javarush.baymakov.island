package entity;

import config.Config;
import config.Constants;
import config.EatingProbability;
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
            decreaseSatiety(Constants.ForPredator.decreaseSatiety);
        }
    }

    protected boolean tryEatAnimals(Location location) {
        List<Animal> animals = location.getAnimalObjects();
        boolean isVeryHungry = currentSatiety < foodNeeded * Constants.ForPredator.decreaseSatiety;
        Config.CannibalismConfig cannibalismConfig = Config.getInstance().getConfig().getCannibalism();

        for (Animal prey : animals) {
            if (prey == null || !prey.isAlive()) {
                continue;
            }

            boolean isCannibalism = prey.getAnimalType().equals(getAnimalType());

            if (isCannibalism) {
                if (cannibalismConfig == null) {
                    continue;
                }

                double cannibalismProb = cannibalismConfig.getCannibalismProbability();
                double minHunger = cannibalismConfig.getMinHungerForCannibalism();
                double maxWeightRatio = cannibalismConfig.getMaxPreyWeightRatio();

                boolean isHungryEnough = (Constants.ForPredator.completeSatiety - (currentSatiety / foodNeeded)) >= minHunger;
                boolean isPreySmaller = prey.getFoodValue() < this.weight * maxWeightRatio;

                if (isHungryEnough && isPreySmaller && getRandom().nextDouble() < cannibalismProb) {
                    if (location.removeAnimal(prey)) {
                        double satietyMultiplier = cannibalismConfig.getCannibalismSatietyMultiplier();
                        double satietyGain = prey.getFoodValue() * satietyMultiplier;
                        increaseSatiety(satietyGain);

                        prey.die();

                        if (getRandom().nextInt(10) < Constants.ForPredator.probabilityOfConsoleOutput) {
                            location.getIsland().addCannibalismEvent(getAnimalType(), satietyGain);
                        }
                        return true;
                    }
                }
                continue;
            }

            if (prey.getFoodValue() > this.weight * 2) {
                continue;
            }

            int probability = EatingProbability.getProbability(this.getClass(), prey.getClass());
            int adjustedProbability = isVeryHungry ? Math.min(100, probability + Constants.ForPredator.bonusToChanceWhenVeryHungry) : probability;

            if (adjustedProbability > 0 && getRandom().nextInt(100) < adjustedProbability) {
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
                Constants.ForPredator.moveProbability,
                Constants.ForPredator.decreaseSatiety);
    }

    @Override
    public void multiply(Location currentLocation) {
        commonMultiply(currentLocation,
                Constants.ForPredator.reproductionProbability,
                Constants.ForPredator.satietyDecreasePerBaby);
    }

    @Override
    public void die() {
        super.die();
    }
}
