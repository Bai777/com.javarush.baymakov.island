import simulation.Scheduler;
import simulation.Simulation;

public class App {
    static void main() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("          СИМУЛЯЦИЯ ОСТРОВА  ");
        System.out.println("=".repeat(60));

        Simulation simulation = new Simulation();

        simulation.initializeTestLocation();

        Scheduler scheduler = new Scheduler(simulation);
        scheduler.start();

        System.out.println("\nСимуляция запущена на 30 секунд...\n");
        try {
            Thread.sleep(30000); // 30 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scheduler.stop();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("       СИМУЛЯЦИЯ ЗАВЕРШЕНА");
        System.out.println("=".repeat(60));
    }
}
