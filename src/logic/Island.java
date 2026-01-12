package logic;

import config.Settings;
import entity.Animal;

import java.util.*;

public class Island {
    private Location[][] locations;

    public Island() {
        this.locations = new Location[Settings.ISLAND_WIDTH][Settings.ISLAND_HEIGHT];
        initializeLocations();
    }

    private void initializeLocations() {
        for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
            for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
                locations[x][y] = new Location();
            }
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < Settings.ISLAND_WIDTH && y >= 0 && y < Settings.ISLAND_HEIGHT) {
            return locations[x][y];
        }
        return null;
    }

    public void growPlants() {
        for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
            for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
                Location location = locations[x][y];
                int growth = new Random().nextInt(5) + 1;
                location.addPlants(growth);
            }
        }
    }

    public void printStatistics() {
        Map<Class<? extends Animal>, Integer> animalCounts = new HashMap<>();
        int totalPlants = 0;
        int totalAnimals = 0;
        int deadAnimals = 0;

        for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
            for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
                Location location = locations[x][y];
                totalPlants += location.getPlantsCount();

                List<Animal> animals = location.getAnimalObjects();
                totalAnimals += animals.size();

                for (Animal animal : animals) {
                    if (!animal.isAlive()) {
                        deadAnimals++;
                    }

                    Class<? extends Animal> animalClass = animal.getClass();
                    animalCounts.put(animalClass, animalCounts.getOrDefault(animalClass, 0) + 1);
                }
            }
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("СТАТИСТИКА ОСТРОВА - ТАКТ: " + System.currentTimeMillis());
        System.out.println("=".repeat(60));
        System.out.println("Размер: " + Settings.ISLAND_WIDTH + "x" + Settings.ISLAND_HEIGHT);
        System.out.println("Растения всего: " + totalPlants);
        System.out.println("Животные всего: " + totalAnimals + " (мертвых: " + deadAnimals + ")");
        System.out.println("-".repeat(60));

        if (!animalCounts.isEmpty()) {
            System.out.println("РАСПРЕДЕЛЕНИЕ ПО ВИДАМ:");

            List<Map.Entry<Class<? extends Animal>, Integer>> sortedEntries = new ArrayList<>(animalCounts.entrySet());
            sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            for (Map.Entry<Class<? extends Animal>, Integer> entry : sortedEntries) {
                String animalName = entry.getKey().getSimpleName();
                int count = entry.getValue();
                System.out.printf("  %-15s: %d%n", animalName, count);
            }
        } else {
            System.out.println("Животных нет");
        }

        System.out.println("=".repeat(60));
    }

    public int getWidth() {
        return Settings.ISLAND_WIDTH;
    }

    public int getHeight() {
        return Settings.ISLAND_HEIGHT;
    }
}

