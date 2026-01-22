package logic;

import config.Config;
import config.Constants;
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
                Integer currentPlants = location.getPlantsCount();
                Integer maxPlants = islandConfig.getMaxPlantsInCell();

                if (currentPlants < maxPlants) {
                    int availableSpace = maxPlants - currentPlants;

                    Double fillRatio = (double) (currentPlants / maxPlants);
                    Integer growthChance = (int) ((1.0 - fillRatio) * 100);

                    if (random.nextInt(100) < growthChance) {
                        Integer growth = random.nextInt(3) + 1;
                        location.addPlants(Math.min(growth, availableSpace));
                    }
                }
            }
        }
    }

        public void printStatistics () {
            Map<String, Integer> animalCounts = new LinkedHashMap<>();
            Map<String, Integer> deadAnimalCounts = new LinkedHashMap<>();

            Integer totalPlants = 0;
            Integer totalAnimals = 0;
            Integer totalDeadAnimals = 0;
            Integer totalLiveAnimals = 0;

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

            System.out.println("\n" + "=".repeat(Constants.ForIsland.repeat));
            System.out.println(Constants.ForIsland.statistic + "(" + getWidth() + "x" + getHeight() + ")");
            System.out.println("=".repeat(Constants.ForIsland.repeat));
            System.out.println(Constants.ForIsland.totalNumberOfPlants + totalPlants);
            System.out.println(Constants.ForIsland.totalNumberOfAnimals + totalAnimals +
                    " (живых: " + totalLiveAnimals +
                    ", мертвых: " + totalDeadAnimals + ")");
            System.out.println("-".repeat(Constants.ForIsland.repeat));

            System.out.println(Constants.ForIsland.dieAnimals);
            if (!deadAnimalCounts.isEmpty()) {
                printSortedMap(deadAnimalCounts);
            } else {
                System.out.println(Constants.ForIsland.notDieAnimals);
            }
            System.out.println("-".repeat(60));

            System.out.println(Constants.ForIsland.liveAnimals);
            if (!animalCounts.isEmpty()) {
                printSortedMap(animalCounts);
            } else {
                System.out.println(Constants.ForIsland.notLiveAnimals);
            }

            System.out.println("=".repeat(Constants.ForIsland.repeat));
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
                System.out.println(Constants.ForIsland.error + e.getMessage());
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

