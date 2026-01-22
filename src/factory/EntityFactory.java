package factory;

import config.Config;
import config.Constants;
import entity.Animal;
import entity.animals.herbivores.*;
import entity.animals.predators.*;
import entity.plants.Plant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class EntityFactory {
    private final Map<String, Supplier<Animal>> animalSuppliers;
    private final Config config;

    private static EntityFactory instance;

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
        return instance;
    }

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

    public Animal createBabyAnimal(String animalType) {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig(animalType);
        if (cfg == null) return null;

        return createAnimalWithConfig(animalType, cfg);
    }

    private Animal createAnimalWithConfig(String animalType, Config.AnimalConfig cfg) {
        Supplier<Animal> supplier = animalSuppliers.get(animalType.toLowerCase());
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException(Constants.ForEntityFactory.messageFromException + animalType);
    }


    public Animal createAnimal(String animalType) {
        Supplier<Animal> supplier = animalSuppliers.get(animalType.toLowerCase());
        if (supplier != null) {
            return supplier.get();
        }
        throw new IllegalArgumentException(Constants.ForEntityFactory.messageFromException + animalType);
    }

    private Animal createEagle() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("eagle");
        return new Eagle(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createBear() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("bear");
        return new Bear(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createFox() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("fox");
        return new Fox(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createBoa() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("boa");
        return new Boa(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createCaterpillar() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("caterpillar");
        return new Caterpillar(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createDuck() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("duck");
        return new Duck(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createBuffalo() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("buffalo");
        return new Buffalo(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createBoar() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("boar");
        return new Boar(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createGoat() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("goat");
        return new Goat(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createMouse() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("mouse");
        return new Mouse(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createDeer() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("deer");
        return new Deer(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
    }

    private Animal createHorse() {
        Config.AnimalConfig cfg = config.getConfig().getAnimals().getAnimalConfig("horse");
        return new Horse(cfg.getWeight(), cfg.getMaxCountInCell(), cfg.getSpeed(), cfg.getFoodNeeded());
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
