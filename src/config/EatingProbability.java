package config;

import entity.Animal;
import entity.Herbivore;
import entity.animals.herbivores.*;
import entity.animals.predators.*;

import java.util.HashMap;
import java.util.Map;

public class EatingProbability {
    private static final Map<Class<? extends Animal>, Map<Class<? extends Animal>, Integer>> probabilityMatrix;

    static {
        probabilityMatrix = new HashMap<>();
        initMatrix();
    }

    private static void initMatrix() {
        Map<Class<? extends Animal>, Integer> wolfMap = new HashMap<>();
        wolfMap.put(Horse.class, 10);
        wolfMap.put(Deer.class, 15);
        wolfMap.put(Rabbit.class, 60);
        wolfMap.put(Mouse.class, 80);
        wolfMap.put(Goat.class, 60);
        wolfMap.put(Sheep.class, 70);
        wolfMap.put(Boar.class, 15);
        wolfMap.put(Buffalo.class, 10);
        wolfMap.put(Duck.class, 40);
        probabilityMatrix.put(Wolf.class, wolfMap);

        Map<Class<? extends Animal>, Integer> boaMap = new HashMap<>();
        boaMap.put(Fox.class, 15);
        boaMap.put(Rabbit.class, 20);
        boaMap.put(Mouse.class, 40);
        boaMap.put(Duck.class, 10);
        probabilityMatrix.put(Boa.class, boaMap);

        Map<Class<? extends Animal>, Integer> foxMap = new HashMap<>();
        foxMap.put(Rabbit.class, 70);
        foxMap.put(Mouse.class, 90);
        foxMap.put(Duck.class, 60);
        foxMap.put(Caterpillar.class, 40);
        probabilityMatrix.put(Fox.class, foxMap);

        Map<Class<? extends Animal>, Integer> bearMap = new HashMap<>();
        bearMap.put(Boa.class, 80);
        bearMap.put(Horse.class, 40);
        bearMap.put(Deer.class, 80);
        bearMap.put(Rabbit.class, 80);
        bearMap.put(Mouse.class, 90);
        bearMap.put(Goat.class, 70);
        bearMap.put(Sheep.class, 70);
        bearMap.put(Boar.class, 50);
        bearMap.put(Buffalo.class, 20);
        bearMap.put(Duck.class, 10);
        probabilityMatrix.put(Bear.class, bearMap);

        Map<Class<? extends Animal>, Integer> eagleMap = new HashMap<>();
        eagleMap.put(Fox.class, 10);
        eagleMap.put(Rabbit.class, 90);
        eagleMap.put(Mouse.class, 90);
        eagleMap.put(Duck.class, 80);
        probabilityMatrix.put(Eagle.class, eagleMap);

        Map<Class<? extends Animal>, Integer> mouseMap = new HashMap<>();
        mouseMap.put(Caterpillar.class, 90);
        probabilityMatrix.put(Mouse.class, mouseMap);

        Map<Class<? extends Animal>, Integer> boarMap = new HashMap<>();
        boarMap.put(Mouse.class, 50);
        boarMap.put(Caterpillar.class, 90);
        probabilityMatrix.put(Boar.class, boarMap);

        Map<Class<? extends Animal>, Integer> duckMap = new HashMap<>();
        duckMap.put(Caterpillar.class, 90);
        probabilityMatrix.put(Duck.class, duckMap);
    }

    public static int getProbability(Class<? extends Animal> predator,
                                     Class<? extends Animal> prey) {
        Map<Class<? extends Animal>, Integer> preyMap = probabilityMatrix.get(predator);
        if (preyMap != null) {
            return preyMap.getOrDefault(prey, 0);
        }
        return 0;
    }

    public static boolean eatsPlants(Class<? extends Animal> animalClass) {
        if (Herbivore.class.isAssignableFrom(animalClass)) {
            return true;
        }

        return false;
    }

    public static boolean hasAnimalPrey(Class<? extends Animal> animalClass) {
        Map<Class<? extends Animal>, Integer> preyMap = probabilityMatrix.get(animalClass);
        return preyMap != null && !preyMap.isEmpty();
    }
}

