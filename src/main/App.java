package main;

import config.Config;
import config.Constants;
import simulation.Scheduler;
import simulation.Simulation;

public class App {
    static void main() {
        System.out.println(Constants.ForApp.title);
        System.out.println("=".repeat(Constants.ForApp.repeat));

        Config config = Config.getInstance();
        Simulation simulation = new Simulation();
        simulation.initializeCustomDistribution();

        Scheduler scheduler = new Scheduler(simulation);
        scheduler.start();

        int simulationDurationSeconds = config.getConfig().getSimulation().getMaxSimulationDurationSeconds();

        try {
            Thread.sleep(simulationDurationSeconds * 1000L);
        } catch (InterruptedException e) {
            System.out.println(Constants.ForApp.error + e.getMessage());
        }

        scheduler.stop();

        System.out.println(Constants.ForApp.titleEnd);
        simulation.getIsland().printStatistics();
    }
}
