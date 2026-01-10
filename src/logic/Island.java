package logic;

import config.Settings;
import entity.Animal;

import java.util.HashMap;
import java.util.Map;

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
                location.addPlants(10);
            }
        }
    }

    public void printStatistics() {
        Map<Class<? extends Animal>, Integer> animalCounts = new HashMap<>();
        int totalPlants = 0;

        for (int x = 0; x < Settings.ISLAND_WIDTH; x++) {
            for (int y = 0; y < Settings.ISLAND_HEIGHT; y++) {
                Location location = locations[x][y];
                totalPlants += location.getPlantsCount();

                Map<Class<? extends Animal>, Integer> locationAnimals = location.getAnimals();
                for (Map.Entry<Class<? extends Animal>, Integer> entry : locationAnimals.entrySet()) {
                    Class<? extends Animal> animalClass = entry.getKey();
                    int count = entry.getValue();
                    animalCounts.put(animalClass, animalCounts.getOrDefault(animalClass, 0) + count);
                }
            }
        }

        System.out.println("\n" + "=".repeat(40));
        System.out.println("СТАТИСТИКА ОСТРОВА");
        System.out.println("=".repeat(40));
        System.out.println("Размер: " + Settings.ISLAND_WIDTH + "x" + Settings.ISLAND_HEIGHT);
        System.out.println("Растений всего: " + totalPlants);
        System.out.println("Макс. в клетке: " + Settings.MAX_PLANTS_IN_CELL);
        System.out.println("-".repeat(40));

        if (!animalCounts.isEmpty()) {
            System.out.println("ЖИВОТНЫЕ:");
            for (Map.Entry<Class<? extends Animal>, Integer> entry : animalCounts.entrySet()) {
                String animalName = entry.getKey().getSimpleName();
                int count = entry.getValue();
                System.out.printf("  %-15s: %d%n", animalName, count);
            }
        } else {
            System.out.println("Животных нет");
        }

        System.out.println("=".repeat(40));
    }

    public int getWidth() {
        return Settings.ISLAND_WIDTH;
    }

    public int getHeight() {
        return Settings.ISLAND_HEIGHT;
    }
}

