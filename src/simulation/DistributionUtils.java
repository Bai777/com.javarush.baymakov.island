package simulation;

import entity.Animal;
import entity.plants.Plant;
import factory.EntityFactory;
import logic.Island;
import logic.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DistributionUtils {
    private static final Random RANDOM = new Random();
    private static final EntityFactory FACTORY = EntityFactory.getInstance();

    private DistributionUtils() {}

    public static int distributeEntities(Island island, int totalCount, int maxPerCell) {
        return distributePlantsWithDensity(island, totalCount, maxPerCell);
    }

    public static int distributeAnimalsSmart(Island island, String animalType,
                                             int totalCount, int maxPerCell) {
        if (totalCount <= 0 || island == null || animalType == null) {
            return 0;
        }

        Integer width = island.getWidth();
        Integer height = island.getHeight();
        Integer totalCells = width * height;

        if (totalCells == 0) return 0;

        Integer animalsPerCell = Math.min(maxPerCell, (int) Math.ceil((double) totalCount / totalCells));

        Integer added = 0;

        for (int x = 0; x < width && added < totalCount; x++) {
            for (int y = 0; y < height && added < totalCount; y++) {
                Location location = island.getLocation(x, y);
                if (location == null) {
                    continue;
                }

                Integer currentCount = location.getAnimalCount(animalType);
                Integer canAdd = Math.min(animalsPerCell - currentCount, totalCount - added);

                for (int i = 0; i < canAdd; i++) {
                    Animal animal = FACTORY.createAnimal(animalType);
                    if (animal != null && location.addAnimal(animal)) {
                        added++;
                    }
                }
            }
        }

        return added;
    }

    public static int distributePlantsWithDensity(Island island, int totalCount, int maxPerCell) {
        if (totalCount <= 0 || island == null) {
            return 0;
        }

        Integer width = island.getWidth();
        Integer height = island.getHeight();
        Integer totalCells = width * height;

        if (totalCells == 0) return 0;

        Integer basePlantsPerCell = totalCount / totalCells;
        Integer remainder = totalCount % totalCells;

        int added = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Location location = island.getLocation(x, y);
                if (location == null) {
                    continue;
                }

                Integer plantsToAdd = basePlantsPerCell;
                if (remainder > 0) {
                    plantsToAdd++;
                    remainder--;
                }

                Integer currentPlants = location.getPlantsCount();
                plantsToAdd = Math.min(plantsToAdd, maxPerCell - currentPlants);
                plantsToAdd = Math.max(0, plantsToAdd);

                if (plantsToAdd > 0 && location.addPlants(plantsToAdd)) {
                    added += plantsToAdd;
                }
            }
        }

        return added;
    }
}
