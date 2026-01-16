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
        try {
            for (int x = startX; x < startX + width && x < island.getWidth(); x++) {
                for (int y = startY; y < startY + height && y < island.getHeight(); y++) {
                    Location location = island.getLocation(x, y);
                    if (location != null) {
                        processLocation(location, x, y);
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Error in AnimalLifecycleTask: " + e.getMessage());
        }
        return null;
    }

    private void processLocation(Location location, int x, int y) {
        try {
            List<Animal> animals = new ArrayList<>(location.getAnimalObjects());

            for (Animal animal : animals) {
                if (animal == null || !animal.isAlive()) continue;

                animal.eat(location);

                if (animal.isAlive()) {
                    animal.multiply(location);
                }

                if (animal.isAlive() && animal.getSpeed() > 0) {
                    List<Location> adjacentLocations = getAdjacentLocations(x, y);
                    animal.move(location, adjacentLocations);
                }

                animal.decreaseSatiety(0.3);
            }

            location.cleanDeadAnimals();
        }catch (Exception e) {
            System.err.println("Error processing location [" + x + "," + y + "]: " + e.getMessage());
        }
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
