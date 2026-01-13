import config.Config;
import simulation.Scheduler;
import simulation.Simulation;

public class App {
    static void main() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          СИМУЛЯЦИЯ ОСТРОВА - YAML КОНФИГУРАЦИЯ");
        System.out.println("=".repeat(60));

        Config config = Config.getInstance();

        System.out.println("Загружена конфигурация:");
        System.out.println("  Размер острова: " + config.getConfig().getIsland().getWidth() +
                "x" + config.getConfig().getIsland().getHeight());
        System.out.println("  Травоядных видов: " + config.getConfig().getAnimals().getHerbivores().size());
        System.out.println("  Хищников видов: " + config.getConfig().getAnimals().getPredators().size());

        Simulation simulation = new Simulation();

        System.out.println("\n" + "-".repeat(60));
        simulation.initializeCustomDistribution();

        Scheduler scheduler = new Scheduler(simulation);
        scheduler.start();

        int simulationDurationSeconds = config.getConfig().getSimulation().getMaxSimulationDurationSeconds();
        System.out.println("\nСимуляция запущена на " + simulationDurationSeconds + " секунд...\n");

        try {
            Thread.sleep(simulationDurationSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.stop();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("       СИМУЛЯЦИЯ ЗАВЕРШЕНА");
        System.out.println("=".repeat(60));

        simulation.getIsland().printStatistics();

        System.out.println("\n" + "-".repeat(60));
        System.out.println("ПРОДОЛЖИТЕЛЬНОСТЬ СИМУЛЯЦИИ: " + simulationDurationSeconds + " секунд");
        System.out.println("-".repeat(60));
    }
}
