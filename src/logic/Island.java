package logic;

import config.Config;
import entity.Animal;
import factory.EntityFactory;

import java.util.*;

public class Island {
    private final Location[][] locations;
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
                int currentPlants = location.getPlantsCount();
                int maxPlants = islandConfig.getMaxPlantsInCell();

                if (currentPlants < maxPlants) {
                    int availableSpace = maxPlants - currentPlants;

                    double fillRatio = (double) currentPlants / maxPlants;
                    int growthChance = (int) ((1.0 - fillRatio) * 100);

                    if (random.nextInt(100) < growthChance) {
                        int growth = random.nextInt(3) + 1;
                        location.addPlants(Math.min(growth, availableSpace));
                    }
                }
            }
        }
    }

        public void printStatistics () {
            Map<String, Integer> animalCounts = new LinkedHashMap<>();
            Map<String, Integer> deadAnimalCounts = new LinkedHashMap<>();

            int totalPlants = 0;
            int totalAnimals = 0;
            int totalDeadAnimals = 0;
            int totalLiveAnimals = 0;

            for (int x = 0; x < islandConfig.getWidth(); x++) {
                for (int y = 0; y < islandConfig.getHeight(); y++) {
                    Location location = locations[x][y];
                    if (location != null) {
                        totalPlants += location.getPlantsCount();

                        List<Animal> animals = location.getAllAnimalsIncludingDead();
                        totalAnimals += animals.size();

                        for (Animal animal : animals) {
                            if (animal != null) {
                                String animalType = animal.getAnimalType();
                                if (!animal.isAlive()) {
                                    totalDeadAnimals++;
                                    deadAnimalCounts.put(animalType, deadAnimalCounts.getOrDefault(animalType, 0) + 1);
                                } else {
                                    totalLiveAnimals++;
                                    animalCounts.put(animalType, animalCounts.getOrDefault(animalType, 0) + 1);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("СТАТИСТИКА ОСТРОВА (" + getWidth() + "x" + getHeight() + ")");
            System.out.println("=".repeat(60));
            System.out.println("Общее количество растений: " + totalPlants);
            System.out.println("Общее количество животных: " + totalAnimals +
                    " (живых: " + totalLiveAnimals +
                    ", мертвых: " + totalDeadAnimals + ")");
            System.out.println("-".repeat(60));

            System.out.println("УМЕРШИЕ ЖИВОТНЫЕ:");
            if (!deadAnimalCounts.isEmpty()) {
                printSortedMap(deadAnimalCounts);
            } else {
                System.out.println("  Нет умерших животных");
            }
            System.out.println("-".repeat(60));

            System.out.println("ЖИВЫЕ ЖИВОТНЫЕ ПО ВИДАМ:");
            if (!animalCounts.isEmpty()) {
                printSortedMap(animalCounts);
            } else {
                System.out.println("  Нет живых животных");
            }

            System.out.println("=".repeat(60));
        }

    public void cleanAllDeadAnimals() {
        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                Location location = locations[x][y];
                if (location != null) {
                    location.cleanDeadAnimals();
                }
            }
        }
    }

    public void printStatisticsAndClean() {
        printStatistics();

        cleanAllDeadAnimals();
    }

        private void printSortedMap(Map < String, Integer > deadAnimalCounts){
            List<Map.Entry<String, Integer>> sortedDead = new ArrayList<>(deadAnimalCounts.entrySet());
            sortedDead.sort((a, b) -> b.getValue().compareTo(a.getValue()));

            for (Map.Entry<String, Integer> entry : sortedDead) {
                printAnimalEntry(entry);
            }
        }

        private void printAnimalEntry(Map.Entry < String, Integer > entry){
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

        public int getWidth () {
            return islandConfig.getWidth();
        }

        public int getHeight () {
            return islandConfig.getHeight();
        }
    }

