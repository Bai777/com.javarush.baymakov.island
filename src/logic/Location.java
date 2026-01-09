package logic;

import config.Settings;
import entity.Animal;

import java.util.HashMap;
import java.util.Map;

public class Location {
    private Map<Class<? extends Animal>, Integer> animals;
    private int plantsCount;

    public Location() {
        this.animals = new HashMap<>();
        this.plantsCount = 0;
    }

    public boolean addAnimal(Animal animal) {
        Class<? extends Animal> animalClass = animal.getClass();
        int currentCount = animals.getOrDefault(animalClass, 0);
        int maxCount = animal.getMaxCountInCell();

        if (currentCount < maxCount) {
            animals.put(animalClass, currentCount + 1);
            return true;
        }
        return false;
    }

    public boolean removeAnimal(Class<? extends Animal> animalClass) {
        int count = animals.getOrDefault(animalClass, 0);
        if (count > 0) {
            animals.put(animalClass, count - 1);
            return true;
        }
        return false;
    }

    public int getAnimalCount(Class<? extends Animal> animalClass) {
        return animals.getOrDefault(animalClass, 0);
    }

    public Map<Class<? extends Animal>, Integer> getAnimals() {
        return new HashMap<>(animals);
    }

    public void addPlants(int count) {
        plantsCount += count;
        if (plantsCount > Settings.MAX_PLANTS_IN_CELL) {
            plantsCount = Settings.MAX_PLANTS_IN_CELL;
        }
    }

    public boolean removePlants(int count) {
        if (plantsCount >= count) {
            plantsCount -= count;
            return true;
        }
        return false;
    }

    public int getPlantsCount() {
        return plantsCount;
    }

    public void setPlantsCount(int plantsCount) {
        this.plantsCount = Math.min(plantsCount, Settings.MAX_PLANTS_IN_CELL);
    }

    public boolean hasSpaceFor(Class<? extends Animal> animalClass, Animal animal) {
        int currentCount = animals.getOrDefault(animalClass, 0);
        return currentCount < animal.getMaxCountInCell();
    }

}
