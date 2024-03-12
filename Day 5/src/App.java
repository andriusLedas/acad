import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadLocalRandom;
import java.time.LocalDate;

public class App {

    public static void main(String[] args) throws InterruptedException {

        Set<String> runnerIDs = Runner.getAllRunnersIDs();
        long startTime = System.currentTimeMillis();
//
//        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
//            for (String id : runnerIDs) {
//                executorService.submit(() -> {
//                    try {
//                        System.out.println(Runner.getRunnerById(id).getName());
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            }
//        }
//        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));
        AtomicInteger currentNumber = new AtomicInteger(0);
        List<Runner> runners = Collections.synchronizedList(new ArrayList<>());

        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (String id : runnerIDs) {
                executorService.submit(() -> {
                    try {
                        Runner runnerToBuild = Runner.getRunnerById(id);
                        Runner runner = Runner.Builder.builder()
                                .withPersonalId(id)
                                .withName(runnerToBuild.getName())
                                .withStartingNumber(currentNumber.incrementAndGet())
                                .withBirthDate(generateRandomBirthDate())
                                .withAdvantagePoints(0)
                                .build();
                        runners.add(runner);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        List<AdvantageCalculation> advantageCalculations = new ArrayList<>() {
            {
                add(calculateWeekendAdvantage());
                add(calculateNotWinterAdvantage());
                add(calculateStartingPointAdvantage());
            }
        };

        setAdvantagePointsOfRunners(runners, advantageCalculations);

        runners.sort(Comparator.comparing(Runner::getStartingNumber));
        System.out.println(runners);
        System.out.println("Execution time: " + (System.currentTimeMillis() - startTime));

        //end of main method
    }

    private static AdvantageCalculation calculateWeekendAdvantage() {
        return runner -> {
            int dayOfWeekValue = runner.getBirthDate().getDayOfWeek().getValue();
            return (dayOfWeekValue > 5) ? 3 : 0;
        };
    }

    private static AdvantageCalculation calculateNotWinterAdvantage() {
        return runner -> {
            int monthValue = runner.getBirthDate().getMonthValue();
            return (monthValue < 12 && monthValue > 2) ? 2 : 0;
        };
    }

    private static AdvantageCalculation calculateStartingPointAdvantage() {
        return runner -> {
            int startingPoint = runner.getStartingNumber();
            return (startingPoint <= 25) ? 1 : 0;
        };
    }

    private static void setAdvantagePointsOfRunners(
            List<Runner> runners, List<AdvantageCalculation> advantageCalculations) {

        for (Runner runner : runners) {
            for (AdvantageCalculation advantageCalculation : advantageCalculations) {
                int currentAdvantagePoints = runner.getAdvantagePoints();
                int advantagePointsToAdd = advantageCalculation.calculateAdvantage(runner);
                runner.setAdvantagePoints(currentAdvantagePoints + advantagePointsToAdd);
            }
        }
    }

    private static LocalDate generateRandomBirthDate() {
        // generates dates so that runners are 18 to 40 years old
        LocalDate minDate = LocalDate.now().minusYears(40);
        LocalDate maxDate = LocalDate.now().minusYears(18);

        long minDay = minDate.toEpochDay();
        long maxDay = maxDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay + 1);

        return LocalDate.ofEpochDay(randomDay);
    }
}
