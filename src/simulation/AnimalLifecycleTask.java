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
                    processLocation(location, x, y);
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

            animal.eat(location);

            animal.multiply(location);

            if (animal.isAlive()) {
                List<Location> adjacentLocations = getAdjacentLocations(x, y);
                animal.move(location, adjacentLocations);
            }

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
