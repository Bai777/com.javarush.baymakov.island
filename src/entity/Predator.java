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
        Map<String, Integer> animals = location.getAnimals();

        for (Map.Entry<String, Integer> entry : animals.entrySet()) {
            String preyType = entry.getKey();
            int count = entry.getValue();

            if (preyType.equals(getAnimalType())) {
                continue;
            }

            Class<? extends Animal> preyClass = getAnimalClassFromType(preyType);
            if (preyClass == null) continue;

            int probability = getEatingProbability(preyClass);

            if (probability > 0 && count > 0 && getRandom().nextInt(100) < probability) {

                Animal prey = location.removeAnimal(preyType);

                if (prey != null && prey.isAlive()) {
                    increaseSatiety(prey.getWeight());
                    return true;
                }
            }
        }

        return false;
    }

    private Class<? extends Animal> getAnimalClassFromType(String animalType) {
        switch (animalType.toLowerCase()) {
            case "wolf":
                return entity.animals.predators.Wolf.class;
            case "boa":
                return entity.animals.predators.Boa.class;
            case "fox":
                return entity.animals.predators.Fox.class;
            case "bear":
                return entity.animals.predators.Bear.class;
            case "eagle":
                return entity.animals.predators.Eagle.class;
            case "horse":
                return entity.animals.herbivores.Horse.class;
            case "deer":
                return entity.animals.herbivores.Deer.class;
            case "rabbit":
                return entity.animals.herbivores.Rabbit.class;
            case "mouse":
                return entity.animals.herbivores.Mouse.class;
            case "goat":
                return entity.animals.herbivores.Goat.class;
            case "sheep":
                return entity.animals.herbivores.Sheep.class;
            case "boar":
                return entity.animals.herbivores.Boar.class;
            case "buffalo":
                return entity.animals.herbivores.Buffalo.class;
            case "duck":
                return entity.animals.herbivores.Duck.class;
            case "caterpillar":
                return entity.animals.herbivores.Caterpillar.class;
            default:
                return null;
        }
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

                int targetCount = currentLocation.getAnimalCount(getAnimalType());
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
        public void multiply (Location currentLocation){
            if (!isAlive()) return;

            int sameTypeCount = getCountInLocation(currentLocation);
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
        public void die () {
            super.die();
        }
    }
