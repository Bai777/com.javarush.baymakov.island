package config;


import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Config {
    private static Config instance;
    private final SimulationConfig config;

    private Config() {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(new File("simulation-config.yaml"), SimulationConfig.class);
        } catch (IOException e) {
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
        
        public IslandConfig getIsland() { return island; }
        public PlantsConfig getPlants() { return plants; }
        public AnimalsConfig getAnimals() { return animals; }
        public ReproductionConfig getReproduction() { return reproduction; }
        public SimulationSettings getSimulation() { return simulation; }
    }

    public static class IslandConfig {
        private int width;
        private int height;
        private int max_plants_in_cell;
        private long plant_growth_interval_ms;
        private long statistics_interval_ms;
        private long animal_lifecycle_interval_ms;

        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public int getMaxPlantsInCell() { return max_plants_in_cell; }
        public long getPlantGrowthIntervalMs() { return plant_growth_interval_ms; }
        public long getStatisticsIntervalMs() { return statistics_interval_ms; }
        public long getAnimalLifecycleIntervalMs() { return animal_lifecycle_interval_ms; }
    }

    public static class PlantsConfig {
        private double weight;
        public double getWeight() { return weight; }
    }

    public static class AnimalsConfig {
        private Map<String, AnimalConfig> herbivores = new HashMap<>();
        private Map<String, AnimalConfig> predators = new HashMap<>();

        public Map<String, AnimalConfig> getHerbivores() { return herbivores; }
        public Map<String, AnimalConfig> getPredators() { return predators; }

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
        private double weight;
        private int max_count_in_cell;
        private int speed;
        private double food_needed;
        private String icon;
        private double hunt_probability;
        private List<String> prey_types;

        public double getWeight() { return weight; }
        public int getMaxCountInCell() { return max_count_in_cell; }
        public int getSpeed() { return speed; }
        public double getFoodNeeded() { return food_needed; }
        public String getIcon() { return icon; }
        public double getHuntProbability() { return hunt_probability; }
        public List<String> getPreyTypes() { return prey_types != null ? prey_types : new ArrayList<>(); }
    }

    public static class ReproductionConfig {
        private int min_animals_for_reproduction;
        private double reproduction_probability;
        private int max_newborns_per_pair;

        public int getMinAnimalsForReproduction() { return min_animals_for_reproduction; }
        public double getReproductionProbability() { return reproduction_probability; }
        public int getMaxNewbornsPerPair() { return max_newborns_per_pair; }
    }

    public static class SimulationSettings {
        private int initial_plants_count;
        private int section_size_for_threads;
        private double satiety_decrease_per_tick;
        private int max_simulation_duration_seconds;

        public int getInitialPlantsCount() { return initial_plants_count; }
        public int getSectionSizeForThreads() { return section_size_for_threads; }
        public double getSatietyDecreasePerTick() { return satiety_decrease_per_tick; }
        public int getMaxSimulationDurationSeconds() { return max_simulation_duration_seconds; }
    }
}
