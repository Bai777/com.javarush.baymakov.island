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
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        try {
            System.out.println("\n=== НАСТРОЙКА СИМУЛЯЦИИ ===");

            System.out.print("Введите ширину острова (по умолчанию 100): ");
            String widthInput = scanner.nextLine();
            int width = widthInput.isEmpty() ? 100 : Integer.parseInt(widthInput);

            System.out.print("Введите высоту острова (по умолчанию 20): ");
            String heightInput = scanner.nextLine();
            int height = heightInput.isEmpty() ? 20 : Integer.parseInt(heightInput);

            Config.getInstance().getConfig().getIsland().width = width;
            Config.getInstance().getConfig().getIsland().height = height;

            this.island = new Island();

            System.out.print("Введите количество животных каждого типа (по умолчанию 2): ");
            String countInput = scanner.nextLine();
            int count = countInput.isEmpty() ? 2 : Integer.parseInt(countInput);

            System.out.print("Введите время симуляции в секундах (по умолчанию 300): ");
            String timeInput = scanner.nextLine();
            int simulationTime = timeInput.isEmpty() ? 300 : Integer.parseInt(timeInput);
            Config.getInstance().getConfig().getSimulation().maxSimulationDurationSeconds = simulationTime;

            System.out.println("\n" + "=".repeat(50));
            System.out.println("ПАРАМЕТРЫ СИМУЛЯЦИИ:");
            System.out.println("  Размер острова: " + width + "x" + height);
            System.out.println("  Животных каждого типа: " + count);
            System.out.println("  Время симуляции: " + simulationTime + " секунд");
            System.out.println("=".repeat(50) + "\n");

            initializeRandomDistribution(count);

        } catch (Exception e) {
            System.out.println("Ошибка ввода. Используются значения по умолчанию.");
            initializeRandomDistribution(2);
        }
    }
}
