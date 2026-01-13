package simulation;

import config.Config;
import logic.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Scheduler {
    private ScheduledExecutorService scheduledExecutorService;
    private ExecutorService animalLifecycleExecutor;
    private Simulation simulation;
    private int threadPoolSize;
    private final Config.SimulationSettings simulationSettings;


    public Scheduler(Simulation simulation, Config.SimulationSettings simulationSettings) {
        this.simulation = simulation;
        this.simulationSettings = simulationSettings;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(3);
        this.threadPoolSize = Runtime.getRuntime().availableProcessors();
        this.animalLifecycleExecutor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        Config config = Config.getInstance();
        Config.IslandConfig islandConfig = config.getConfig().getIsland();
        Config.SimulationSettings simSettings = config.getConfig().getSimulation();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("ЗАПУСК МНОГОПОТОЧНОЙ СИМУЛЯЦИИ");
        System.out.println("=".repeat(50));
        System.out.println("Настройки из конфигурации:");
        System.out.println("  Размер острова: " + islandConfig.getWidth() + "x" + islandConfig.getHeight());
        System.out.println("  Размер пула потоков: " + threadPoolSize);
        System.out.println("  Интервал роста растений: " + islandConfig.getPlantGrowthIntervalMs() + " мс");
        System.out.println("  Интервал жизненного цикла: " + islandConfig.getAnimalLifecycleIntervalMs() + " мс");
        System.out.println("  Интервал статистики: " + islandConfig.getStatisticsIntervalMs() + " мс");
        System.out.println("=".repeat(50));

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            simulation.getIsland().growPlants();
            System.out.println("[Система] Растения выросли на всех локациях");
        }, 0, islandConfig.getPlantGrowthIntervalMs(), TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            executeAnimalLifecycle();
        }, 1000, islandConfig.getAnimalLifecycleIntervalMs(), TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            simulation.getIsland().printStatistics();
        }, 0, islandConfig.getStatisticsIntervalMs(), TimeUnit.MILLISECONDS);

        System.out.println("Планировщик успешно запущен!");
    }

    private void executeAnimalLifecycle() {
        Island island = simulation.getIsland();
        int width = island.getWidth();
        int height = island.getHeight();

        int sectionSize = simulationSettings.getSectionSizeForThreads();
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int x = 0; x < width; x += sectionSize) {
            for (int y = 0; y < height; y += sectionSize) {
                int taskWidth = Math.min(sectionSize, width - x);
                int taskHeight = Math.min(sectionSize, height - y);

                tasks.add(new AnimalLifecycleTask(island, x, y, taskWidth, taskHeight));
            }
        }

        try {
            List<Future<Void>> futures = animalLifecycleExecutor.invokeAll(tasks);

            for (Future<Void> future : futures) {
                future.get();
            }

            System.out.println("[Система] Жизненный цикл животных завершен для всего острова");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("\nОстановка планировщика...");

        scheduledExecutorService.shutdown();
        animalLifecycleExecutor.shutdown();

        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }

            if (!animalLifecycleExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                animalLifecycleExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
            animalLifecycleExecutor.shutdownNow();
        }

        System.out.println("Планировщик остановлен!");
    }
}
