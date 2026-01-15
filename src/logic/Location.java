package logic;

import config.Config;
import entity.Animal;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final ReentrantLock lock = new ReentrantLock();
    private Map<String, Integer> animals;
    private int plantsCount;
    private List<Animal> animalObjects;
    private final int maxPlantsInCell;

    public Location() {
        Config config = Config.getInstance();
        this.maxPlantsInCell = config.getConfig().getIsland().getMaxPlantsInCell();
        this.animals = new HashMap<>();
        this.plantsCount = 0;
        this.animalObjects = new ArrayList<>();
    }

    public boolean addAnimal(Animal animal) {
        lock.lock();
        try {
            String animalType = animal.getAnimalType();
            int currentCount = animals.getOrDefault(animalType, 0);
            int maxCount = animal.getMaxCountInCell();

            if (currentCount < maxCount && !animalObjects.contains(animal)) {
                animals.put(animalType, currentCount + 1);
                animalObjects.add(animal);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean removeAnimal(Animal animal) {
        lock.lock();
        try {
            String animalType = animal.getAnimalType();
            if (animalObjects.remove(animal)) {
                int count = animals.getOrDefault(animalType, 0);
                if (count > 0) {
                    animals.put(animalType, count - 1);
                }
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public List<Animal> getAnimalObjects() {
        lock.lock();
        try {
            return new ArrayList<>(animalObjects);
        } finally {
            lock.unlock();
        }
    }

    public void cleanDeadAnimals() {
        lock.lock();
        try {
            Iterator<Animal> iterator = animalObjects.iterator();
            while (iterator.hasNext()) {
                Animal animal = iterator.next();
                if (!animal.isAlive()) {
                    iterator.remove();
                    String animalType = animal.getAnimalType();
                    int currentCount = animals.getOrDefault(animalType, 0);
                    if (currentCount > 0) {
                        animals.put(animalType, currentCount - 1);
                    }
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public int getAnimalCount(String animalType) {
        lock.lock();
        try {
            return animals.getOrDefault(animalType, 0);
        } finally {
            lock.unlock();
        }
    }

    public Map<String, Integer> getAnimals() {
        lock.lock();
        try {
            return new HashMap<>(animals);
        } finally {
            lock.unlock();
        }
    }

    public void addPlants(int count) {
        lock.lock();
        try {
            plantsCount += count;
            if (plantsCount > maxPlantsInCell) {
                plantsCount = maxPlantsInCell;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean removePlants(int count) {
        lock.lock();
        try {
            if (plantsCount >= count) {
                plantsCount -= count;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public int getPlantsCount() {
        lock.lock();
        try {
            return plantsCount;
        } finally {
            lock.unlock();
        }
    }

    public void setPlantsCount(int plantsCount) {
        lock.lock();
        try {
            this.plantsCount = Math.min(plantsCount, maxPlantsInCell);
        } finally {
            lock.unlock();
        }
    }
}

