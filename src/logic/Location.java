package logic;

import config.Config;
import config.Constants;
import entity.Animal;
import entity.plants.Plant;
import factory.EntityFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final ReentrantLock lock = new ReentrantLock();
    private final Map<String, Integer> animals;
    private final List<Plant> plants;
    private final List<Animal> animalObjects;
    private final int maxPlantsInCell;
    private final EntityFactory factory;
    private final Island island;

    public Location(Island island) {
        Config config = Config.getInstance();
        this.maxPlantsInCell = config.getConfig().getIsland().getMaxPlantsInCell();
        this.animals = new HashMap<>();
        this.plants = new ArrayList<>();
        this.animalObjects = new ArrayList<>();
        this.factory = EntityFactory.getInstance();
        this.island = island;
    }

    public Island getIsland() {
        return island;
    }

    public boolean addAnimal(Animal animal) {
        lock.lock();
        try {
            String animalType = animal.getAnimalType();
            Integer currentCount = animals.getOrDefault(animalType, Constants.ForLocation.defaultValue);
            Integer maxCount = animal.getMaxCountInCell();

            if (currentCount < maxCount && !animalObjects.contains(animal)) {
                animals.put(animalType, currentCount + Constants.ForLocation.increventValue);
                animalObjects.add(animal);
                animal.setCurrentLocation(this);
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public List<Animal> getAllAnimalsIncludingDead() {
        lock.lock();
        try {
            return new ArrayList<>(animalObjects);
        } finally {
            lock.unlock();
        }
    }

    public boolean removeAnimal(Animal animal) {
        lock.lock();
        try {
            if (animal == null) {
                return false;
            }
            String animalType = animal.getAnimalType();
            if (animalObjects.remove(animal)) {
                Integer count = animals.getOrDefault(animalType, 0);
                if (count > 0) {
                    animals.put(animalType, count - 1);
                }
                if (animals.getOrDefault(animalType, 0) <= 0) {
                    animals.remove(animalType);
                }
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public List<Animal> getAnimalObjects() {
        return getAllAnimalsIncludingDead();
    }

    public void cleanDeadAnimals() {
        lock.lock();
        try {
            List<Animal> animalsToRemove = new ArrayList<>();

            for (Animal animal : animalObjects) {
                if (animal == null || !animal.isAlive()) {
                    animalsToRemove.add(animal);
                }
            }

            for (Animal deadAnimal : animalsToRemove) {
                if (deadAnimal != null) {
                    String animalType = deadAnimal.getAnimalType();
                    animalObjects.remove(deadAnimal);

                    Integer currentCount = this.animals.getOrDefault(animalType, 0);
                    if (currentCount > 0) {
                        this.animals.put(animalType, currentCount - 1);
                    }

                    if (this.animals.getOrDefault(animalType, 0) <= 0) {
                        this.animals.remove(animalType);
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

    public boolean addPlants(int count) {
        lock.lock();
        try {
            if (plants.size() + count > maxPlantsInCell) {
                return false;
            }

            for (int i = 0; i < count; i++) {
                plants.add(factory.createPlant());
            }
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean removePlants(int count) {
        lock.lock();
        try {
            if (plants.size() >= count) {
                for (int i = 0; i < count; i++) {
                    if (!plants.isEmpty()) {
                        plants.remove(0);
                    }
                }
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
            return plants.size();
        } finally {
            lock.unlock();
        }
    }

    public List<Plant> getPlants() {
        lock.lock();
        try {
            return new ArrayList<>(plants);
        } finally {
            lock.unlock();
        }
    }
}

