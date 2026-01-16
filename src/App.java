import config.Config;
import simulation.Scheduler;
import simulation.Simulation;

public class App {
    static void main(String[] args) {
        System.out.println("\nСИМУЛЯЦИЯ ОСТРОВА");
        System.out.println("=".repeat(40));

        Config config = Config.getInstance();

        Simulation simulation = new Simulation();

        simulation.initializeCustomDistribution();

        Scheduler scheduler = new Scheduler(simulation);
        scheduler.start();

        int simulationDurationSeconds = config.getConfig().getSimulation().getMaxSimulationDurationSeconds();

        try {
            Thread.sleep(simulationDurationSeconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.stop();

        System.out.println("\nСимуляция завершена!");
        simulation.getIsland().printStatistics();
    }
}
