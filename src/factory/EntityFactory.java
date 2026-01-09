package factory;

import entity.Animal;
import entity.plants.Plant;

public class EntityFactory {
    public Animal createAnimal(Class<? extends Animal> animalClass) {
        try {
            return animalClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Plant createPlant() {
        return new Plant();
    }
}
