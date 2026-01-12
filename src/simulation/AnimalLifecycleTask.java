package simulation;

import entity.Animal;
import logic.Island;
import logic.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class AnimalLifecycleTask implements Callable<Void> {
    private final Island island;
    private final int startX;
    private final int startY;
    private final int width;
    private final int height;

    private static final int DEBUG_X = 50;
    private static final int DEBUG_Y = 10;
    private static boolean firstRun = true;

    public AnimalLifecycleTask(Island island, int startX, int startY, int width, int height) {
        this.island = island;
        this.startX = startX;
        this.startY = startY;
        this.width = width;
        this.height = height;
    }

    @Override
    public Void call() throws Exception {
        for (int x = startX; x < startX + width && x < island.getWidth(); x++) {
            for (int y = startY; y < startY + height && y < island.getHeight(); y++) {
                Location location = island.getLocation(x, y);
                if (location != null) {
                    if (x == DEBUG_X && y == DEBUG_Y && firstRun) {
                        System.out.println("\n[ОТЛАДКА] Тестовая локация [" + x + "," + y + "]");
                        System.out.println("  Животных до цикла: " + location.getAnimalObjects().size());
                        System.out.println("  Растений до цикла: " + location.getPlantsCount());
                    }

                    processLocation(location, x, y);

                    if (x == DEBUG_X && y == DEBUG_Y && firstRun) {
                        System.out.println("  Животных после цикла: " + location.getAnimalObjects().size());
                        System.out.println("  Растений после цикла: " + location.getPlantsCount());
                        firstRun = false;
                    }
                }
            }
        }
        return null;
    }

    private void processLocation(Location location, int x, int y) {
        List<Animal> animals = location.getAnimalObjects();

        List<Animal> animalsCopy = new ArrayList<>(animals);

        for (Animal animal : animalsCopy) {
            if (!animal.isAlive()) continue;

            if (x == DEBUG_X && y == DEBUG_Y) {
                System.out.println("    Обработка: " + animal.getClass().getSimpleName() +
                        " (сытость: " + animal.getCurrentSatiety() + ")");
            }

            animal.eat(location);

            animal.multiply(location);

            List<Location> adjacentLocations = getAdjacentLocations(x, y);

            animal.move(location, adjacentLocations);

            animal.decreaseSatiety(0.1);
        }

        location.cleanDeadAnimals();
    }

    private List<Location> getAdjacentLocations(int x, int y) {
        List<Location> adjacent = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (newX >= 0 && newX < island.getWidth() &&
                    newY >= 0 && newY < island.getHeight()) {
                Location loc = island.getLocation(newX, newY);
                if (loc != null) {
                    adjacent.add(loc);
                }
            }
        }

        return adjacent;
    }
}
