package simulation;

import config.Config;
import config.Constants;
import factory.EntityFactory;
import logic.Island;

import java.util.Random;
import java.util.Scanner;

public class Simulation {
    private Island island;
    private EntityFactory factory;
    private Config config;
    Random random;

    public Simulation() {
        this.island = new Island();
        this.factory = new EntityFactory();
        this.config = Config.getInstance();
        this.random = new Random();
    }

    public void initializeRandomDistribution(int animalsPerType) {
        Config.AnimalsConfig animalsConfig = config.getConfig().getAnimals();

        System.out.println("\n" + "=".repeat(Constants.ForSimulation.repeat));
        System.out.println(Constants.ForSimulation.title);
        System.out.println("=".repeat(Constants.ForSimulation.repeat));

        Integer totalAnimalsAdded = 0;

        for (String herbivoreType : animalsConfig.getHerbivores().keySet()) {
            Config.AnimalConfig animalConfig = factory.getAnimalConfig(herbivoreType);
            if (animalConfig == null) continue;

            Integer added = DistributionUtils.distributeAnimalsSmart(
                    island,
                    herbivoreType,
                    animalsPerType,
                    animalConfig.getMaxCountInCell()
            );
            totalAnimalsAdded += added;
            System.out.printf("  %-15s: %d животных%n", herbivoreType, added);
        }

        for (String predatorType : animalsConfig.getPredators().keySet()) {
            Config.AnimalConfig animalConfig = factory.getAnimalConfig(predatorType);
            if (animalConfig == null) continue;

            Integer added = DistributionUtils.distributeAnimalsSmart(
                    island,
                    predatorType,
                    animalsPerType,
                    animalConfig.getMaxCountInCell()
            );
            totalAnimalsAdded += added;
            System.out.printf("  %-15s: %d животных%n", predatorType, added);
        }

        initializePlants();

        System.out.println("-".repeat(Constants.ForSimulation.repeat));
        System.out.println("Итого добавлено: " + totalAnimalsAdded + " животных");
        System.out.println("=".repeat(Constants.ForSimulation.repeat));
    }

    private void initializePlants() {
        Config.SimulationSettings simSettings = config.getConfig().getSimulation();
        int initialPlants = simSettings.getInitialPlantsCount();

        if (initialPlants <= 0) {
            System.out.println(Constants.ForSimulation.message);
            return;
        }

        int maxPlantsPerCell = config.getConfig().getIsland().getMaxPlantsInCell();
        int added = DistributionUtils.distributeEntities(
                island, initialPlants, maxPlantsPerCell
        );

        System.out.println(Constants.ForSimulation.plantDistributionReport + added + " из " + initialPlants);
    }

    public Island getIsland() {
        return island;
    }

    public void initializeCustomDistribution() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("\n=== НАСТРОЙКА СИМУЛЯЦИИ ===");

            int width = getValidIntegerInput(scanner,
                    "Введите ширину острова (по умолчанию 100): ",
                    100, 1, 1000);

            int height = getValidIntegerInput(scanner,
                    "Введите высоту острова (по умолчанию 20): ",
                    20, 1, 1000);

            config.getConfig().getIsland().width = width;
            config.getConfig().getIsland().height = height;

            this.island = new Island();

            int animalsPerType = getValidIntegerInput(scanner,
                    "Введите количество животных каждого типа (по умолчанию 2): ",
                    2, 2, 1000);

            int simulationTime = getValidIntegerInput(scanner,
                    "Введите время симуляции в секундах (по умолчанию 300): ",
                    300, 1, 86400);
            Config.getInstance().getConfig().getSimulation().maxSimulationDurationSeconds = simulationTime;

            System.out.println("\n" + "=".repeat(50));
            System.out.println("ПАРАМЕТРЫ СИМУЛЯЦИИ:");
            System.out.println("  Размер острова: " + width + "x" + height);
            System.out.println("  Животных каждого типа: " + animalsPerType);
            System.out.println("  Время симуляции: " + simulationTime + " секунд");
            System.out.println("=".repeat(50) + "\n");

            if (animalsPerType > 0) {
                initializeRandomDistribution(animalsPerType);
            } else {
                System.out.println("Животные не добавлены (количество = 0)");
                initializePlants();
            }

        } catch (Exception e) {
            System.err.println("Критическая ошибка инициализации: " + e.getMessage());
            e.printStackTrace();
            System.out.println("Используются значения по умолчанию.");

            config.getConfig().getIsland().width = 100;
            config.getConfig().getIsland().height = 20;
            this.island = new Island();
            initializeRandomDistribution(2);

        } finally {
            scanner.close();
        }
    }

    private int getValidIntegerInput(Scanner scanner, String prompt, int defaultValue, int minValue, int maxValue) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            try {
                int value = Integer.parseInt(input);
                if (value < minValue) {
                    System.out.println("Ошибка: значение должно быть не меньше " + minValue + "!");
                    continue;
                }

                if (value > maxValue) {
                    System.out.println("Ошибка: значение должно быть не больше " + maxValue + "!");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное целое число!");
            }
        }
    }
}
