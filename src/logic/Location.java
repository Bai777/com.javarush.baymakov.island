package logic;

import config.Config;
import entity.Animal;
import entity.plants.Plant;
import factory.EntityFactory;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Location {
    private final ReentrantLock lock = new ReentrantLock();
    private Map<String, Integer> animals;
    private List<Plant> plants;
    private List<Animal> animalObjects;
    private final int maxPlantsInCell;
    private final EntityFactory factory;

    public Location() {
        Config config = Config.getInstance();
        this.maxPlantsInCell = config.getConfig().getIsland().getMaxPlantsInCell();
        this.animals = new HashMap<>();
        this.plants = new ArrayList<>();
        this.animalObjects = new ArrayList<>();
        this.factory = EntityFactory.getInstance();;
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
                int count = animals.getOrDefault(animalType, 0);
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

            int deadCount = 0;
            Map<String, Integer> deadByType = new HashMap<>();

            for (Animal animal : animalObjects) {
                if (animal == null || !animal.isAlive()) {
                    deadCount++;
                    String animalType = animal.getAnimalType();
                    deadByType.put(animalType, deadByType.getOrDefault(animalType, 0) + 1);
                    animalsToRemove.add(animal);
                }
            }

            if (deadCount > 0) {
                System.out.println("[Система] Найдено " + deadCount + " мертвых животных");
            }

            for (Animal deadAnimal : animalsToRemove) {
                if (deadAnimal != null) {
                    String animalType = deadAnimal.getAnimalType();
                    animalObjects.remove(deadAnimal);

                    int currentCount = this.animals.getOrDefault(animalType, 0);
                    if (currentCount > 0) {
                        this.animals.put(animalType, currentCount - 1);
                    }

                    if (this.animals.getOrDefault(animalType, 0) <= 0) {
                        this.animals.remove(animalType);
                    }
                }
            }
            animalObjects.removeIf(Objects::isNull);

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

