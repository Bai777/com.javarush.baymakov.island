import config.Settings;
import simulation.Scheduler;
import simulation.Simulation;

public class App {
    static void main() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          СИМУЛЯЦИЯ ОСТРОВА - ЭКОСИСТЕМА");
        System.out.println("=".repeat(60));

        System.out.println("\nНАСТРОЙКИ ПРОЕКТА:");
        System.out.println("  1. Размер острова: " + Settings.ISLAND_WIDTH + "x" + Settings.ISLAND_HEIGHT);
        System.out.println("  2. Макс. растений в клетке: " + Settings.MAX_PLANTS_IN_CELL);
        System.out.println("  3. Интервалы:");
        System.out.println("     - Рост растений: " + Settings.PLANT_GROWTH_INTERVAL_MS + " мс");
        System.out.println("     - Статистика: " + Settings.STATISTICS_INTERVAL_MS + " мс");

        System.out.println("\n  4. Количество видов:");
        System.out.println("     - Хищников: 5");
        System.out.println("     - Травоядных: 10");
        System.out.println("     - Растений: 1");
        System.out.println("\n" + "-".repeat(60));

        Simulation simulation = new Simulation();
        simulation.initializeTestLocation();

        Scheduler scheduler = new Scheduler(simulation);
        scheduler.start();

        System.out.println("\nСимуляция запущена на 20 секунд...\n");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.stop();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("       СИМУЛЯЦИЯ ЗАВЕРШЕНА");
        System.out.println("=".repeat(60));

    }
}
