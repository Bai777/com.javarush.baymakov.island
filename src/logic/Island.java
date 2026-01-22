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

    private final Map<String, Integer> deadAnimalsLastPeriod = new HashMap<>();
    private final Map<String, Integer> deadAnimalsTotal = new HashMap<>();
    private final List<String> events = new ArrayList<>();

    public Island() {
        this.config = Config.getInstance();
        this.islandConfig = config.getConfig().getIsland();
        this.locations = new Location[islandConfig.getWidth()][islandConfig.getHeight()];
        initializeLocations();
    }

    private void initializeLocations() {
        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                locations[x][y] = new Location(this);
            }
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < islandConfig.getWidth() && y >= 0 && y < islandConfig.getHeight()) {
            return locations[x][y];
        }
        return null;
    }

    public void addReproductionEvent(String animalType, int count) {
        synchronized (events) {
            events.add("[Размножение] " + animalType + ": родилось " + count + " детенышей");
        }
    }

    public void addCannibalismEvent(String animalType, double satietyGain) {
        synchronized (events) {
            events.add("[Каннибализм] " + animalType + " съел сородича (+" + String.format("%.2f", satietyGain) + " сытости)");
        }
    }

    public void printEvents() {
        synchronized (events) {
            if (!events.isEmpty()) {
                for (String event : events) {
                    System.out.println(event);
                }
                events.clear();
            }
        }
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

    public void addDeadAnimal(String animalType) {
        synchronized (deadAnimalsLastPeriod) {
            deadAnimalsLastPeriod.put(animalType,
                    deadAnimalsLastPeriod.getOrDefault(animalType, 0) + 1);
            deadAnimalsTotal.put(animalType,
                    deadAnimalsTotal.getOrDefault(animalType, 0) + 1);
        }
    }

    public void clearDeadAnimalsLastPeriod() {
        synchronized (deadAnimalsLastPeriod) {
            deadAnimalsLastPeriod.clear();
        }
    }

    public Map<String, Integer> getDeadAnimalsLastPeriod() {
        synchronized (deadAnimalsLastPeriod) {
            return new HashMap<>(deadAnimalsLastPeriod);
        }
    }

    public void printStatistics() {
        Map<String, Integer> animalCounts = new LinkedHashMap<>();
        Map<String, Integer> deadAnimalsLastPeriod = getDeadAnimalsLastPeriod();

        Integer totalPlants = 0;
        Integer totalAnimals = 0;
        Integer totalLiveAnimals = 0;
        Integer totalDeadAnimalsLastPeriod = 0;

        for (Integer count : deadAnimalsLastPeriod.values()) {
            totalDeadAnimalsLastPeriod += count;
        }

        for (int x = 0; x < islandConfig.getWidth(); x++) {
            for (int y = 0; y < islandConfig.getHeight(); y++) {
                Location location = locations[x][y];
                if (location != null) {
                    totalPlants += location.getPlantsCount();

                    List<Animal> animals = location.getAnimalObjects();
                    totalAnimals += animals.size();

                    for (Animal animal : animals) {
                        if (animal != null && animal.isAlive()) {
                            String animalType = animal.getAnimalType();
                            animalCounts.put(animalType, animalCounts.getOrDefault(animalType, 0) + 1);
                            totalLiveAnimals++;
                        }
                    }
                }
            }
        }

        System.out.println("\n" + "=".repeat(Constants.ForIsland.repeat));
        System.out.println(Constants.ForIsland.statistic + "(" + getWidth() + "x" + getHeight() + ")");
        System.out.println("=".repeat(Constants.ForIsland.repeat));
        System.out.println(Constants.ForIsland.totalNumberOfPlants + totalPlants);
        System.out.println(Constants.ForIsland.totalNumberOfAnimals + totalAnimals);
        System.out.println("-".repeat(Constants.ForIsland.repeat));

        System.out.println(Constants.ForIsland.dieAnimalsLastPeriod +
                (totalDeadAnimalsLastPeriod > 0 ? " (всего: " + totalDeadAnimalsLastPeriod + ")" : ""));
        if (!deadAnimalsLastPeriod.isEmpty()) {
            printSortedMap(deadAnimalsLastPeriod, false);
        } else {
            System.out.println(Constants.ForIsland.notDieAnimalsLastPeriod);
        }
        System.out.println("-".repeat(Constants.ForIsland.repeat));

        System.out.println(Constants.ForIsland.liveAnimals);
        if (!animalCounts.isEmpty()) {
            printSortedMap(animalCounts, true);
        } else {
            System.out.println(Constants.ForIsland.notLiveAnimals);
        }

        System.out.println("=".repeat(Constants.ForIsland.repeat));

        clearDeadAnimalsLastPeriod();
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
        printEvents();
        printStatistics();
        cleanAllDeadAnimals();
    }

    private void printSortedMap(Map<String, Integer> map, boolean showIcons) {
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(map.entrySet());
        sorted.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entry : sorted) {
            String animalName = entry.getKey();
            int count = entry.getValue();

            if (showIcons) {
                showIcon(animalName, count);
            } else {
                System.out.printf("  %-15s: %d%n", animalName, count);
            }
        }
    }

    private void showIcon(String animalName, int count) {
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

    public int getWidth() {
        return islandConfig.getWidth();
    }

    public int getHeight() {
        return islandConfig.getHeight();
    }
}

