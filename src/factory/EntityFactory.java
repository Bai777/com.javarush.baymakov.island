package factory;

import config.Config;
import entity.Animal;
import entity.animals.herbivores.Rabbit;
import entity.plants.Plant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntityFactory {
    private final Map<String, Supplier<Animal>> animalSuppliers;
    private final Config config;

    public EntityFactory() {
        this.config = Config.getInstance();
        this.animalSuppliers = new HashMap<>();
        registerAnimalSuppliers();
    }

    private void registerAnimalSuppliers() {
        animalSuppliers.put("rabbit", () -> createRabbit());
        animalSuppliers.put("sheep", () -> createSheep());
        animalSuppliers.put("horse", () -> createHorse());
        animalSuppliers.put("deer", () -> createDeer());
        animalSuppliers.put("mouse", () -> createMouse());
        animalSuppliers.put("goat", () -> createGoat());
        animalSuppliers.put("boar", () -> createBoar());
        animalSuppliers.put("buffalo", () -> createBuffalo());
        animalSuppliers.put("duck", () -> createDuck());
        animalSuppliers.put("caterpillar", () -> createCaterpillar());

        animalSuppliers.put("wolf", () -> createWolf());
        animalSuppliers.put("boa", () -> createBoa());
        animalSuppliers.put("fox", () -> createFox());
        animalSuppliers.put("bear", () -> createBear());
        animalSuppliers.put("eagle", () -> createEagle());
    }

    public Animal createAnimal(String animalType) {
        Supplier<Animal> supplier = animalSuppliers.get(animalType.toLowerCase());
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException("Unknown animal type: " + animalType);
    }

    private Rabbit createRabbit() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("rabbit");
        return new Rabbit(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Sheep createSheep() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("sheep");
        return new Sheep(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Wolf createWolf() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("wolf");
        return new Wolf(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    public Plant createPlant() {
        double weight = config.getConfig().getPlants().getWeight();
        return new Plant(weight);
    }

    public Config.AnimalConfig getAnimalConfig(String animalType) {
        return config.getConfig().getAnimals().getAnimalConfig(animalType);
    }
}
