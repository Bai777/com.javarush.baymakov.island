package simulation;

import config.Settings;
import entity.Animal;
import entity.animals.herbivores.*;
import entity.animals.predators.*;
import factory.EntityFactory;
import logic.Island;
import logic.Location;

public class Simulation {
    private Island island;
    private EntityFactory factory;

    public Simulation() {
        this.island = new Island();
        this.factory = new EntityFactory();
    }

    public void initializeTestLocation() {
        int centerX = island.getWidth() / 2;
        int centerY = island.getHeight() / 2;

        Location location = island.getLocation(centerX, centerY);

        System.out.println("\n" + "*".repeat(50));
        System.out.println("ИНИЦИАЛИЗАЦИЯ ТЕСТОВОЙ ЛОКАЦИИ");
        System.out.println("*".repeat(50));
        System.out.println("Координаты: [" + centerX + ", " + centerY + "]");
        System.out.println("Параметры:");
        System.out.println("  - Макс. растений в клетке: " + Settings.MAX_PLANTS_IN_CELL);
        System.out.println();

        System.out.println("ДОБАВЛЕНИЕ ХИЩНИКОВ:");
        addAnimalWithCheck(location, Wolf.class, Settings.AnimalParams.WOLF_MAX_COUNT_IN_CELL, "Волк", 2);
        addAnimalWithCheck(location, Bear.class, Settings.AnimalParams.BEAR_MAX_COUNT_IN_CELL, "Медведь", 1);
        addAnimalWithCheck(location, Fox.class, Settings.AnimalParams.FOX_MAX_COUNT_IN_CELL, "Лиса", 3);
        addAnimalWithCheck(location, Boa.class, Settings.AnimalParams.BOA_MAX_COUNT_IN_CELL, "Удав", 2);
        addAnimalWithCheck(location, Eagle.class, Settings.AnimalParams.EAGLE_MAX_COUNT_IN_CELL, "Орел", 2);

        System.out.println("\nДОБАВЛЕНИЕ ТРАВОЯДНЫХ:");
        addAnimalWithCheck(location, Horse.class, Settings.AnimalParams.HORSE_MAX_COUNT_IN_CELL, "Лошадь", 2);
        addAnimalWithCheck(location, Rabbit.class, Settings.AnimalParams.RABBIT_MAX_COUNT_IN_CELL, "Кролик", 5);
        addAnimalWithCheck(location, Deer.class, Settings.AnimalParams.DEER_MAX_COUNT_IN_CELL, "Олень", 2);
        addAnimalWithCheck(location, Goat.class, Settings.AnimalParams.GOAT_MAX_COUNT_IN_CELL, "Коза", 3);
        addAnimalWithCheck(location, Sheep.class, Settings.AnimalParams.SHEEP_MAX_COUNT_IN_CELL, "Овца", 3);

        location.setPlantsCount(75);
        System.out.println("\nДОБАВЛЕНИЕ РАСТЕНИЙ:");
        System.out.println("  Добавлено растений: 75");

        System.out.println("\n" + "*".repeat(50));
        System.out.println("ИНИЦИАЛИЗАЦИЯ ЗАВЕРШЕНА!");
        System.out.println("*".repeat(50));
    }

    private void addAnimalWithCheck(Location location, Class<? extends Animal> animalClass,
                                    int maxCount, String animalName, int countToAdd) {
        int currentCount = location.getAnimalCount(animalClass);

        for (int i = 0; i < countToAdd; i++) {
            if (currentCount < maxCount) {
                if (location.addAnimal(factory.createAnimal(animalClass))) {
                    currentCount++;
                }
            } else {
                System.out.println("  " + animalName + ": достигнут лимит " + maxCount);
                break;
            }
        }

        if (currentCount > 0) {
            System.out.println("  " + animalName + ": добавлено " + currentCount + "/" + maxCount);
        }
    }

    public Island getIsland() {
        return island;
    }
}
