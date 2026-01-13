package logic;

import entity.Animal;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final ReentrantLock lock = new ReentrantLock();
    private Map<Class<? extends Animal>, Integer> animals;
    private int plantsCount;
    private List<Animal> animalObjects;

    public Location() {
        this.animals = new ConcurrentHashMap<>();
        this.plantsCount = 0;
        this.animalObjects = new CopyOnWriteArrayList<>();
    }

    public boolean addAnimal(Animal animal) {
        lock.lock();
        try {
            Class<? extends Animal> animalClass = animal.getClass();
            int currentCount = animals.getOrDefault(animalClass, 0);
            int maxCount = animal.getMaxCountInCell();

            if (currentCount < maxCount) {
                animals.put(animalClass, currentCount + 1);
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
            Class<? extends Animal> animalClass = animal.getClass();
            int count = animals.getOrDefault(animalClass, 0);

            if (count > 0 && animalObjects.remove(animal)) {
                animals.put(animalClass, count - 1);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public Animal removeAnimal(Class<? extends Animal> animalClass) {
        lock.lock();
        try {
            int count = animals.getOrDefault(animalClass, 0);
            if (count > 0) {
                for (Animal animal : animalObjects) {
                    if (animal.getClass().equals(animalClass)) {
                        if (animalObjects.remove(animal)) {
                            animals.put(animalClass, count - 1);
                            return animal;
                        }
                    }
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public List<Animal> getAnimalObjects() {
        return new ArrayList<>(animalObjects);
    }

    public void cleanDeadAnimals() {
        lock.lock();
        try {
            Iterator<Animal> iterator = animalObjects.iterator();
            while (iterator.hasNext()) {
                Animal animal = iterator.next();
                if (!animal.isAlive()) {
                    iterator.remove();
                    Class<? extends Animal> animalClass = animal.getClass();
                    animals.put(animalClass, animals.get(animalClass) - 1);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public int getAnimalCount(Class<? extends Animal> animalClass) {
        return animals.getOrDefault(animalClass, 0);
    }

    public Map<Class<? extends Animal>, Integer> getAnimals() {
        return new HashMap<>(animals);
    }

    public void addPlants(int count) {
        lock.lock();
        try {
            plantsCount += count;
            if (plantsCount > Settings.MAX_PLANTS_IN_CELL) {
                plantsCount = Settings.MAX_PLANTS_IN_CELL;
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
        return plantsCount;
    }

    public void setPlantsCount(int plantsCount) {
        lock.lock();
        try {
            this.plantsCount = Math.min(plantsCount, Settings.MAX_PLANTS_IN_CELL);
        } finally {
            lock.unlock();
        }
    }
}
