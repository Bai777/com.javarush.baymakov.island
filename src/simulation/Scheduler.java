package simulation;

import config.Config;
import config.Constants;
import logic.Island;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Scheduler {
    private final ScheduledExecutorService scheduledExecutorService;
    private final ExecutorService animalLifecycleExecutor;
    private final Simulation simulation;
    private int threadPoolSize;
    private final Config config;


    public Scheduler(Simulation simulation) {
        this.simulation = simulation;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(Constants.ForScheduler.CORE_POOL_SIZE);
        this.threadPoolSize = Runtime.getRuntime().availableProcessors();
        this.animalLifecycleExecutor = Executors.newFixedThreadPool(threadPoolSize);
        this.config = Config.getInstance();
    }

    public void start() {
        Config.IslandConfig islandConfig = config.getConfig().getIsland();

        System.out.println(Constants.ForScheduler.title);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            simulation.getIsland().growPlants();
            System.out.println(Constants.ForScheduler.messageFromSystem);
        }, 0, islandConfig.getPlantGrowthIntervalMs(), TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            executeAnimalLifecycle();
        }, 1000, islandConfig.getAnimalLifecycleIntervalMs(), TimeUnit.MILLISECONDS);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            simulation.getIsland().printStatisticsAndClean();
        }, 2000, islandConfig.getStatisticsIntervalMs(), TimeUnit.MILLISECONDS);
    }

    private void executeAnimalLifecycle() {
        Island island = simulation.getIsland();
        Integer width = island.getWidth();
        Integer height = island.getHeight();

        Integer sectionSize = config.getConfig().getSimulation().getSectionSizeForThreads();
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int x = 0; x < width; x += sectionSize) {
            for (int y = 0; y < height; y += sectionSize) {
                Integer taskWidth = Math.min(sectionSize, width - x);
                Integer taskHeight = Math.min(sectionSize, height - y);

                tasks.add(new AnimalLifecycleTask(island, x, y, taskWidth, taskHeight));
            }
        }

        try {
            List<Future<Void>> futures = animalLifecycleExecutor.invokeAll(tasks);

            for (Future<Void> future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
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
    }
}
