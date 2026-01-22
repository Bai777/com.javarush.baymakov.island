package config;

public interface Constants {

    interface ForAnimal {
        Integer currentSatietyHalf = 2;
        String errorReproductionBaby = "Ошибка создания детеныша ";
        Integer onePlant = 1;
        Double energyForMultiply = 1.0;
        Integer currentSatietyZero = 0;
        Integer newbornsZero = 0;
        Integer addedCount = 0;
        Double foodNeededForSaturation = 0.5;
        Integer sameTypeCount = 2;
        Double decreaseSatiety = 0.1;
    }

    interface ForPredator {
        Double decreaseSatiety = 0.3;
        Double completeSatiety = 1.0;
        Integer probabilityOfConsoleOutput = 3;
        Integer bonusToChanceWhenVeryHungry = 30;
        Integer moveProbability = 20;
        Integer reproductionProbability = 30;
        Double satietyDecreasePerBaby = 0.3;
    }

    interface ForOmnivore {
        Double decreaseSatiety = 0.3;
        Integer moveProbability = 20;
        Double satietyDecrease = 0.15;
        Integer reproductionProbability = 20;
        Double satietyDecreasePerBaby = 2.0;
    }

    interface ForHerbivore {
        Double decreaseSatiety = 0.3;
        Integer moveProbability = 40;
        Double satietyDecrease = 0.15;
        Integer reproductionProbability = 20;
        Double satietyDecreasePerBaby = 2.0;
    }

    interface ForEntityFactory {
        String messageFromException = "Неизвестный тип животного: ";
    }

    interface ForIsland {
        Integer repeat = 60;
        String statistic = "СТАТИСТИКА ОСТРОВА ";
        String totalNumberOfPlants = "Общее количество растений: ";
        String totalNumberOfAnimals = "Общее количество животных: ";
        String dieAnimals = "УМЕРШИЕ ЖИВОТНЫЕ:";
        String notDieAnimals = "  Нет умерших животных";
        String liveAnimals = "ЖИВЫЕ ЖИВОТНЫЕ ПО ВИДАМ:";
        String notLiveAnimals = "  Нет живых животных";
        String error = "Ошибка: ";
    }

    interface ForLocation {
        Integer defaultValue = 0;
        Integer increventValue = 1;
    }

    interface ForApp {
        Integer repeat = 60;
        String title = "\nСИМУЛЯЦИЯ ОСТРОВА";
        String titleEnd = "\nСимуляция завершена!";
    }

    interface ForAnimalLifecycleTask {
        String error = "Ошибка в AnimalLifecycleTask: ";
        String errorLocation = "Место обработки ошибки ";
        Double decreaseSatiety = 0.3;
    }

    interface ForScheduler {
        Integer CORE_POOL_SIZE = 3;
        String title = "\nСимуляция запущена...\n";
        String messageFromSystem = "[Система] Растения выросли на всех локациях";
    }

    interface ForSimulation {
        Integer repeat = 50;
        String title = "СЛУЧАЙНОЕ РАСПРЕДЕЛЕНИЕ ЖИВОТНЫХ";
        String message = "  Растений: не требуется";
        String plantDistributionReport = "  Растений: распределено ";
    }
}
