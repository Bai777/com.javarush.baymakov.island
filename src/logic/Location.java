package logic;

import config.Settings;
import entity.Animal;

import java.util.*;

public class Location {
    private Map<Class<? extends Animal>, Integer> animals;
    private int plantsCount;
    private List<Animal> animalObjects;

    public Location() {
        this.animals = new HashMap<>();
        this.plantsCount = 0;
        this.animalObjects = new ArrayList<>();
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
            for (Iterator<Animal> iterator = animalObjects.iterator(); iterator.hasNext(); ) {
                Animal animal = iterator.next();
                if (animal.getClass().equals(animalClass)) {
                    iterator.remove();
                    break;
                }
            }
            return true;
        }
        return false;
    }

    public boolean removeAnimal(Animal animal) {
        Class<? extends Animal> animalClass = animal.getClass();
        int count = animals.getOrDefault(animalClass, 0);
        if (count > 0 && animalObjects.remove(animal)) {
            animals.put(animalClass, count - 1);
            return true;
        }
        return false;
    }

    public List<Animal> getAnimalObjects() {
        return new ArrayList<>(animalObjects);
    }

    public void cleanDeadAnimals() {
        Iterator<Animal> iterator = animalObjects.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            if (!animal.isAlive()) {
                iterator.remove();
                Class<? extends Animal> animalClass = animal.getClass();
                animals.put(animalClass, animals.get(animalClass) - 1);
            }
        }
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
}
