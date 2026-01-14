package simulation;

import config.Config;
import entity.Animal;
import factory.EntityFactory;
import logic.Island;
import logic.Location;

public class Simulation {
    private Island island;
    private EntityFactory factory;
    private Config config;

    public Simulation() {
        this.island = new Island();
        this.factory = new EntityFactory();
        this.config = Config.getInstance();
    }

    public void initializeTestLocation() {
        int centerX = island.getWidth() / 2;
        int centerY = island.getHeight() / 2;

        Location location = island.getLocation(centerX, centerY);

        addAnimalWithCheck(location, "wolf", "Волк", 2);
        addAnimalWithCheck(location, "boa", "Удав", 2);
        addAnimalWithCheck(location, "fox", "Лиса", 3);
        addAnimalWithCheck(location, "bear", "Медведь", 1);
        addAnimalWithCheck(location, "eagle", "Орел", 2);

        System.out.println("\nДОБАВЛЕНИЕ ТРАВОЯДНЫХ:");
        addAnimalWithCheck(location, "horse", "Лошадь", 2);
        addAnimalWithCheck(location, "deer", "Олень", 2);
        addAnimalWithCheck(location, "rabbit", "Кролик", 5);
        addAnimalWithCheck(location, "mouse", "Мышь", 4);
        addAnimalWithCheck(location, "goat", "Коза", 3);
        addAnimalWithCheck(location, "sheep", "Овца", 3);
        addAnimalWithCheck(location, "boar", "Кабан", 5);
        addAnimalWithCheck(location, "buffalo", "Буйвол", 7);
        addAnimalWithCheck(location, "duck", "Утка", 3);
        addAnimalWithCheck(location, "caterpillar", "Гусеница", 8);

        int initialPlantsCount = config.getConfig().getSimulation().getInitialPlantsCount();
        location.setPlantsCount(initialPlantsCount);

        System.out.println("\nДОБАВЛЕНИЕ РАСТЕНИЙ:");
        System.out.println("  Добавлено растений: " + initialPlantsCount);

        System.out.println("\n" + "*".repeat(50));
        System.out.println("ИНИЦИАЛИЗАЦИЯ ЗАВЕРШЕНА!");
        System.out.println("  Локация: [" + centerX + "," + centerY + "]");
        System.out.println("  Всего животных: " + location.getAnimalObjects().size());
        System.out.println("  Растений: " + location.getPlantsCount());
        System.out.println("*".repeat(50));
    }

    private void addAnimalWithCheck(Location location, String animalType,
                                    String animalName, int countToAdd) {
        Config.AnimalConfig animalConfig = factory.getAnimalConfig(animalType);
        if (animalConfig == null) {
            System.out.println("  ОШИБКА: Не найдена конфигурация для " + animalName + " (" + animalType + ")");
            return;
        }

        int maxCount = animalConfig.getMaxCountInCell();
        int currentCount = location.getAnimalCount(animalType);

        int addedCount = 0;
        for (int i = 0; i < countToAdd; i++) {
            if (currentCount < maxCount) {
                Animal animal = factory.createAnimal(animalType);
                if (animal != null && location.addAnimal(animal)) {
                    currentCount++;
                    addedCount++;
                }
            } else {
                System.out.println("  " + animalName + ": достигнут лимит " + maxCount);
                break;
            }
        }

        if (addedCount > 0) {
            System.out.println("  " + animalName + ": добавлено " + addedCount + "/" + maxCount);
        }
    }

    public void initializeRandomDistribution(int animalsPerType) {
        Config config = Config.getInstance();
        Config.AnimalsConfig animalsConfig = config.getConfig().getAnimals();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("СЛУЧАЙНОЕ РАСПРЕДЕЛЕНИЕ ЖИВОТНЫХ");
        System.out.println("=".repeat(50));

        int totalAnimalsAdded = 0;

        for (String herbivoreType : animalsConfig.getHerbivores().keySet()) {
            int added = distributeAnimalsRandomly(herbivoreType, animalsPerType);
            totalAnimalsAdded += added;
            System.out.println("  " + herbivoreType + ": " + added + " животных");
        }

        for (String predatorType : animalsConfig.getPredators().keySet()) {
            int added = distributeAnimalsRandomly(predatorType, animalsPerType);
            totalAnimalsAdded += added;
            System.out.println("  " + predatorType + ": " + added + " животных");
        }

        initializePlants();

        System.out.println("-".repeat(50));
        System.out.println("Итого добавлено: " + totalAnimalsAdded + " животных");
        System.out.println("=".repeat(50));
    }

    private int distributeAnimalsRandomly(String animalType, int count) {
        Config config = Config.getInstance();
        Config.AnimalConfig animalConfig = factory.getAnimalConfig(animalType);
        if (animalConfig == null) return 0;

        int added = 0;
        int width = island.getWidth();
        int height = island.getHeight();
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < count; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            Location location = island.getLocation(x, y);
            if (location != null) {
                int currentCount = location.getAnimalCount(animalType);
                if (currentCount < animalConfig.getMaxCountInCell()) {
                    Animal animal = factory.createAnimal(animalType);
                    if (animal != null && location.addAnimal(animal)) {
                        added++;
                    }
                }
            }
        }

        return added;
    }

    private void initializePlants() {
        Config config = Config.getInstance();
        Config.SimulationSettings simSettings = config.getConfig().getSimulation();
        int initialPlants = simSettings.getInitialPlantsCount();

        int width = island.getWidth();
        int height = island.getHeight();
        java.util.Random random = new java.util.Random();

        for (int i = 0; i < initialPlants; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            Location location = island.getLocation(x, y);
            if (location != null) {
                location.addPlants(1);
            }
        }

        System.out.println("  Растений: распределено " + initialPlants + " по всему острову");
    }

    public Island getIsland() {
        return island;
    }

    public void initializeCustomDistribution() {
        System.out.println("\nВыберите режим инициализации:");
        System.out.println("1. Тестовая локация (все животные в центре)");
        System.out.println("2. Случайное распределение (по всему острову)");
        System.out.print("Ваш выбор: ");

        try {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    initializeTestLocation();
                    break;
                case 2:
                    System.out.print("Введите количество животных каждого типа: ");
                    int count = scanner.nextInt();
                    initializeRandomDistribution(count);
                    break;
                default:
                    System.out.println("Неверный выбор. Используется тестовая локация.");
                    initializeTestLocation();
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода. Используется тестовая локация.");
            initializeTestLocation();
        }
    }
}
