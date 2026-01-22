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
        String error = "Ошибка: ";
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
        String totalMessage = "Итого добавлено: ";
        String settingsTitle = "\n=== НАСТРОЙКА СИМУЛЯЦИИ ===";
        String promptWidth = "Введите ширину острова (по умолчанию 100): ";
        Integer defaultValueWidth = 100;
        Integer minValueWidth = 1;
        Integer maxValueWidth = 1000;

        String promptHeight = "Введите высоту острова (по умолчанию 20): ";
        Integer defaultValueHeight = 20;
        Integer minValueHeight = 1;
        Integer maxValueHeight = 1000;

        String promptAnimalsPerType = "Введите количество животных каждого типа (по умолчанию 2): ";
        Integer defaultValueAnimalsPerType = 2;
        Integer minValueAnimalsPerType = 2;
        Integer maxValueAnimalsPerType = 1000;

        String promptSimulationTime = "Введите время симуляции в секундах (по умолчанию 300): ";
        Integer defaultValueSimulationTime = 300;
        Integer minValueSimulationTime = 1;
        Integer maxValueSimulationTime = 86400;

        String parametrSimulationTitle = "ПАРАМЕТРЫ СИМУЛЯЦИИ:";
        String sizeIslandTitle = "  Размер острова: ";
        String animalsTypeTitle = "  Животных каждого типа: ";
        String timeSimulationTitle = "  Время симуляции: ";
        String animalsMessage = "Животные не добавлены (количество = 0)";
        String criticErrorMessage = "Критическая ошибка инициализации: ";
        String defaultMessageSettings = "Используются значения по умолчанию.";
        String errorInputMinValue = "Ошибка: значение должно быть не меньше ";
        String errorInputMaxValue = "Ошибка: значение должно быть не больше ";
        String errorInputValue = "Ошибка: введите корректное целое число!";
    }
}
