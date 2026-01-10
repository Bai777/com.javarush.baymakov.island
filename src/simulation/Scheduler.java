package simulation;

import config.Settings;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {
    private ScheduledExecutorService scheduledExecutorService;
    private Simulation simulation;

    public Scheduler(Simulation simulation) {
        this.simulation = simulation;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(3);
    }

    public void start() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ЗАПУСК ПЛАНИРОВЩИКА");
        System.out.println("=".repeat(50));
        System.out.println("Настройки:");
        System.out.println("  Интервал роста растений: " + Settings.PLANT_GROWTH_INTERVAL_MS + " мс");
        System.out.println("  Интервал статистики: " + Settings.STATISTICS_INTERVAL_MS + " мс");
        System.out.println("=".repeat(50));

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            simulation.getIsland().growPlants();
            System.out.println("[Система] Растения выросли на всех локациях");
        }, 0, Settings.PLANT_GROWTH_INTERVAL_MS, TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            simulation.getIsland().printStatistics();
        }, 0, Settings.STATISTICS_INTERVAL_MS, TimeUnit.MILLISECONDS);

        System.out.println("Планировщик успешно запущен!");
    }

    public void stop() {
        System.out.println("\nОстановка планировщика...");
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
        }
        System.out.println("Планировщик остановлен!");
    }
}
