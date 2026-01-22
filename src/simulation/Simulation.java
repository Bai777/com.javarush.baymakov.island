package simulation;

import config.Config;
import config.Constants;
import factory.EntityFactory;
import logic.Island;

import java.util.Random;
import java.util.Scanner;

public class Simulation {
    private Island island;
    private final EntityFactory factory;
    private final Config config;
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
        System.out.println(Constants.ForSimulation.totalMessage + totalAnimalsAdded + " животных");
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
            System.out.println(Constants.ForSimulation.settingsTitle);

            int width = getValidIntegerInput(scanner,
                    Constants.ForSimulation.promptWidth,
                    Constants.ForSimulation.defaultValueWidth,
                    Constants.ForSimulation.minValueWidth,
                    Constants.ForSimulation.maxValueWidth);

            int height = getValidIntegerInput(scanner,
                    Constants.ForSimulation.promptHeight,
                    Constants.ForSimulation.defaultValueHeight,
                    Constants.ForSimulation.minValueHeight,
                    Constants.ForSimulation.maxValueHeight);

            config.getConfig().getIsland().width = width;
            config.getConfig().getIsland().height = height;

            this.island = new Island();

            int animalsPerType = getValidIntegerInput(scanner,
                    Constants.ForSimulation.promptAnimalsPerType,
                    Constants.ForSimulation.defaultValueAnimalsPerType,
                    Constants.ForSimulation.minValueAnimalsPerType,
                    Constants.ForSimulation.maxValueAnimalsPerType);

            int simulationTime = getValidIntegerInput(scanner,
                    Constants.ForSimulation.promptSimulationTime,
                    Constants.ForSimulation.defaultValueSimulationTime,
                    Constants.ForSimulation.minValueSimulationTime,
                    Constants.ForSimulation.maxValueSimulationTime);
            Config.getInstance().getConfig().getSimulation().maxSimulationDurationSeconds = simulationTime;

            System.out.println("\n" + "=".repeat(Constants.ForSimulation.repeat));
            System.out.println(Constants.ForSimulation.parametrSimulationTitle);
            System.out.println(Constants.ForSimulation.sizeIslandTitle + width + "x" + height);
            System.out.println(Constants.ForSimulation.animalsTypeTitle + animalsPerType);
            System.out.println(Constants.ForSimulation.timeSimulationTitle + simulationTime + " секунд");
            System.out.println("=".repeat(Constants.ForSimulation.repeat) + "\n");

            if (animalsPerType > 0) {
                initializeRandomDistribution(animalsPerType);
            } else {
                System.out.println(Constants.ForSimulation.animalsMessage);
                initializePlants();
            }

        } catch (Exception e) {
            System.err.println(Constants.ForSimulation.criticErrorMessage + e.getMessage());

            System.out.println(Constants.ForSimulation.defaultMessageSettings);

            config.getConfig().getIsland().width = Constants.ForSimulation.defaultValueWidth;
            config.getConfig().getIsland().height = Constants.ForSimulation.defaultValueHeight;
            this.island = new Island();
            initializeRandomDistribution(Constants.ForSimulation.defaultValueAnimalsPerType);

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
                    System.out.println(Constants.ForSimulation.errorInputMinValue + minValue + "!");
                    continue;
                }

                if (value > maxValue) {
                    System.out.println(Constants.ForSimulation.errorInputMaxValue + maxValue + "!");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println(Constants.ForSimulation.errorInputValue);
            }
        }
    }
}
