package config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Config {
    private static Config instance;
    private final SimulationConfig config;

    private Config() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            File configFile = new File("src/simulation-config.yaml");

            if (!configFile.exists()) {
                configFile = new File("simulation-config.yaml");
            }

            if (!configFile.exists()) {
                throw new RuntimeException("Configuration file not found at: " +
                        configFile.getAbsolutePath());
            }

            config = mapper.readValue(configFile, SimulationConfig.class);

        } catch (IOException e) {
            System.err.println("Config loading error: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    public static synchronized Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public SimulationConfig getConfig() {
        return config;
    }

    public static class SimulationConfig {
        private IslandConfig island;
        private PlantsConfig plants;
        private AnimalsConfig animals;
        private ReproductionConfig reproduction;
        private SimulationSettings simulation;

        public IslandConfig getIsland() {
            return island;
        }

        public PlantsConfig getPlants() {
            return plants;
        }

        public AnimalsConfig getAnimals() {
            return animals;
        }

        public ReproductionConfig getReproduction() {
            return reproduction;
        }

        public SimulationSettings getSimulation() {
            return simulation;
        }
    }

    public static class IslandConfig {
        private int width;
        private int height;

        @JsonProperty("max_plants_in_cell")
        private int maxPlantsInCell;


        @JsonProperty("plant_growth_interval_ms")
        private long plantGrowthIntervalMs;


        @JsonProperty("statistics_interval_ms")
        private long statisticsIntervalMs;

        @JsonProperty("animal_lifecycle_interval_ms")
        private long animalLifecycleIntervalMs;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getMaxPlantsInCell() {
            return maxPlantsInCell;
        }

        public long getPlantGrowthIntervalMs() {
            return plantGrowthIntervalMs;
        }

        public long getStatisticsIntervalMs() {
            return statisticsIntervalMs;
        }

        public long getAnimalLifecycleIntervalMs() {
            return animalLifecycleIntervalMs;
        }
    }

    public static class PlantsConfig {
        private double weight;

        public double getWeight() {
            return weight;
        }
    }

    public static class AnimalsConfig {
        private Map<String, AnimalConfig> herbivores = new HashMap<>();
        private Map<String, AnimalConfig> predators = new HashMap<>();

        public Map<String, AnimalConfig> getHerbivores() {
            return herbivores;
        }

        public Map<String, AnimalConfig> getPredators() {
            return predators;
        }

        public AnimalConfig getAnimalConfig(String type) {
            if (herbivores.containsKey(type.toLowerCase())) {
                return herbivores.get(type.toLowerCase());
            }
            return predators.get(type.toLowerCase());
        }

        public Set<String> getAllAnimalTypes() {
            Set<String> allTypes = new HashSet<>();
            allTypes.addAll(herbivores.keySet());
            allTypes.addAll(predators.keySet());
            return allTypes;
        }
    }

    public static class AnimalConfig {

        @JsonProperty("max_count_in_cell")
        private int maxCountInCell;

        @JsonProperty("food_needed")
        private double foodNeeded;

        @JsonProperty("hunt_probability")
        private double huntProbability;

        @JsonProperty("prey_types")
        private List<String> preyTypes;

        private double weight;
        private int speed;
        private String icon;

        public double getWeight() {
            return weight;
        }

        public int getMaxCountInCell() {
            return maxCountInCell;
        }

        public int getSpeed() {
            return speed;
        }

        public double getFoodNeeded() {
            return foodNeeded;
        }

        public String getIcon() {
            return icon;
        }

        public double getHuntProbability() {
            return huntProbability;
        }

        public List<String> getPreyTypes() {
            return preyTypes != null ? preyTypes : new ArrayList<>();
        }
    }

    public static class ReproductionConfig {
        @JsonProperty("min_animals_for_reproduction")
        private int minAnimalsForReproduction;

        @JsonProperty("reproduction_probability")
        private double reproductionProbability;

        @JsonProperty("max_newborns_per_pair")
        private int maxNewbornsPerPair;

        public int getMinAnimalsForReproduction() {
            return minAnimalsForReproduction;
        }

        public double getReproductionProbability() {
            return reproductionProbability;
        }

        public int getMaxNewbornsPerPair() {
            return maxNewbornsPerPair;
        }
    }

    public static class SimulationSettings {
        @JsonProperty("initial_plants_count")
        private int initialPlantsCount;

        @JsonProperty("section_size_for_threads")
        private int sectionSizeForThreads;

        @JsonProperty("satiety_decrease_per_tick")
        private double satietyDecreasePerTick;

        @JsonProperty("max_simulation_duration_seconds")
        private int maxSimulationDurationSeconds;

        public int getInitialPlantsCount() {
            return initialPlantsCount;
        }

        public int getSectionSizeForThreads() {
            return sectionSizeForThreads;
        }

        public double getSatietyDecreasePerTick() {
            return satietyDecreasePerTick;
        }

        public int getMaxSimulationDurationSeconds() {
            return maxSimulationDurationSeconds;
        }
    }
}
