package logic;

import config.Config;
import entity.Animal;

import java.util.*;

public class Island {
    private Location[][] locations;
    private final Config config;
    private final Config.IslandConfig islandConfig;
    private final Random random = new Random();

    public Island() {
        this.config = Config.getInstance();
        this.islandConfig = config.getConfig().getIsland();
        this.locations = new Location[islandConfig.getWidth()][islandConfig.getHeight()];
        initializeLocations();
    }

    private void initializeLocations() {
        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                locations[x][y] = new Location();
            }
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < islandConfig.getWidth() && y >= 0 && y < islandConfig.getHeight()) {
            return locations[x][y];
        }
        return null;
    }

    public void growPlants() {
        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                Location location = locations[x][y];
                int growth = random.nextInt(5) + 1;
                location.addPlants(growth);
            }
        }
    }

    public void printStatistics() {
        Map<String, Integer> animalCounts = new HashMap<>();
        int totalPlants = 0;
        int totalAnimals = 0;
        int deadAnimals = 0;

        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                Location location = locations[x][y];
                totalPlants += location.getPlantsCount();

                List<Animal> animals = location.getAnimalObjects();
                totalAnimals += animals.size();

                for (Animal animal : animals) {
                    if (!animal.isAlive()) {
                        deadAnimals++;
                    }

                    String animalType = animal.getAnimalType();
                    animalCounts.put(animalType, animalCounts.getOrDefault(animalType, 0) + 1);
                }
            }
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("СТАТИСТИКА ОСТРОВА - ТАКТ: " + System.currentTimeMillis());
        System.out.println("=".repeat(60));
        System.out.println("Размер: " + islandConfig.getWidth() + "x" + islandConfig.getHeight());
        System.out.println("Растения всего: " + totalPlants);
        System.out.println("Животные всего: " + totalAnimals + " (мертвых: " + deadAnimals + ")");
        System.out.println("-".repeat(60));

        if (!animalCounts.isEmpty()) {
            System.out.println("РАСПРЕДЕЛЕНИЕ ПО ВИДАМ:");

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(animalCounts.entrySet());
            sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                String animalName = entry.getKey();
                int count = entry.getValue();
                System.out.printf("  %-15s: %d%n", animalName, count);
            }
        } else {
            System.out.println("Животных нет");
        }

        System.out.println("=".repeat(60));
    }

    public int getWidth() {
        return islandConfig.getWidth();
    }

    public int getHeight() {
        return islandConfig.getHeight();
    }
}

