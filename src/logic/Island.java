package logic;

import config.Config;
import entity.Animal;
import factory.EntityFactory;

import java.util.*;

public class Island {
    private Location[][] locations;
    private final Config config;
    private final Config.IslandConfig islandConfig;
    private final Random random = new Random();
    private final EntityFactory factory = EntityFactory.getInstance();

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
        Map<String, Integer> animalCounts = new LinkedHashMap<>();
        Map<String, Integer> deadAnimalCounts = new LinkedHashMap<>();

        int totalPlants = 0;
        int totalAnimals = 0;
        int totalDeadAnimals  = 0;

        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                Location location = locations[x][y];
                totalPlants += location.getPlantsCount();

                List<Animal> animals = location.getAnimalObjects();
                totalAnimals += animals.size();

                for (Animal animal : animals) {
                    String animalType = animal.getAnimalType();
                    if (!animal.isAlive()) {
                        totalDeadAnimals ++;
                        deadAnimalCounts.put(animalType, deadAnimalCounts.getOrDefault(animalType, 0) + 1);
                    }else {
                        animalCounts.put(animalType, animalCounts.getOrDefault(animalType, 0) + 1);
                    }
                }
            }
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("СТАТИСТИКА ОСТРОВА");
        System.out.println("=".repeat(60));
        System.out.println("Общее количество растений: " + totalPlants);
        System.out.println("Общее количество животных: " + totalAnimals +
                " (живых: " + (totalAnimals - totalDeadAnimals ) +
                ", мертвых: " + totalDeadAnimals  + ")");
        System.out.println("-".repeat(60));

        if (!deadAnimalCounts.isEmpty()) {
            System.out.println("-".repeat(70));
            System.out.println("УМЕРШИЕ ЖИВОТНЫЕ:");
            for (Map.Entry<String, Integer> entry : deadAnimalCounts.entrySet()) {
                getEntryKeyAndValue(entry);
            }
        }

        if (!animalCounts.isEmpty()) {
            System.out.println("ЖИВОТНЫЕ ПО ВИДАМ:");

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(animalCounts.entrySet());
            sortedEntries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                getEntryKeyAndValue(entry);
            }
        } else {
            System.out.println("Животных нет на острове");
        }

        System.out.println("=".repeat(60));
    }

    private void getEntryKeyAndValue(Map.Entry<String, Integer> entry) {
        String animalName = entry.getKey();
        int count = entry.getValue();
        String icon = "❓";
        try {
            Config.AnimalConfig animalConfig = factory.getAnimalConfig(animalName);
            if (animalConfig != null && animalConfig.getIcon() != null) {
                icon = animalConfig.getIcon();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.printf("  %s %-15s: %d%n", icon, animalName, count);
    }

    public int getWidth() {
        return islandConfig.getWidth();
    }

    public int getHeight() {
        return islandConfig.getHeight();
    }
}

