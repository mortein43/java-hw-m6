import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import static java.lang.System.out;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        out.println("Завдання 1:");
        final int ARRAY_SIZE = 10; // Розмір масиву
        int[] array = new int[ARRAY_SIZE];

        CountDownLatch latch = new CountDownLatch(1);

        // Потік для заповнення масиву випадковими числами
        Thread fillArrayThread = new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < ARRAY_SIZE; i++) {
                array[i] = random.nextInt(100); // Заповнення масиву числами від 0 до 99
            }
            System.out.println("Масив заповнений: " + Arrays.toString(array));
            latch.countDown(); // Повідомляємо, що масив заповнений
        });

        // Потік для обчислення суми елементів масиву
        Thread sumThread = new Thread(() -> {
            try {
                latch.await(); // Очікуємо заповнення масиву
                int sum = Arrays.stream(array).sum();
                System.out.println("Сума елементів масиву: " + sum);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Потік для обчислення середнього арифметичного значення
        Thread averageThread = new Thread(() -> {
            try {
                latch.await(); // Очікуємо заповнення масиву
                double average = Arrays.stream(array).average().orElse(0);
                System.out.println("Середнє арифметичне значення: " + average);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Запускаємо всі три потоки
        fillArrayThread.start();
        sumThread.start();
        averageThread.start();

        // Очікуємо завершення всіх потоків
        fillArrayThread.join();
        sumThread.join();
        averageThread.join();
        out.println("\n_________________________________\n");


        /*out.println("Завдання 2:");

        System.out.println("Введіть шлях до файлу: ");
        String filePath = scanner.nextLine();

        File inputFile = new File(filePath);
        File primeNumbersFile = new File("src/primes.txt");
        File factorialsFile = new File("src/factorials.txt");

        CountDownLatch latch2 = new CountDownLatch(1);

        // Потік для заповнення файлу випадковими числами
        Thread fillFileThread = new Thread(() -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(inputFile))) {
                Random random = new Random();
                for (int i = 0; i < 100; i++) {
                    writer.println(random.nextInt(10)); // Записуємо числа від 0 до 19
                }
                System.out.println("Файл заповнений випадковими числами.");
                latch2.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Потік для пошуку простих чисел
        Thread primeThread = new Thread(() -> {
            try {
                latch2.await();
                List<Integer> numbers = readNumbersFromFile(inputFile);
                List<Integer> primes = new ArrayList<>();
                for (int num : numbers) {
                    if (isPrime(num)) {
                        primes.add(num);
                    }
                }
                writeNumbersToFile(primeNumbersFile, primes);
                System.out.println("Прості числа записані у файл primes.txt.");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        // Потік для обчислення факторіалів
        Thread factorialThread = new Thread(() -> {
            try {
                latch2.await();
                List<Integer> numbers = readNumbersFromFile(inputFile);
                List<Long> factorials = new ArrayList<>();
                for (int num : numbers) {
                    factorials.add(factorial(num));
                }
                writeNumbersToFile(factorialsFile, factorials);
                System.out.println("Факторіали чисел записані у файл factorials.txt.");
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        // Запускаємо всі три потоки
        fillFileThread.start();
        primeThread.start();
        factorialThread.start();

        // Очікуємо завершення всіх потоків
        fillFileThread.join();
        primeThread.join();
        factorialThread.join();

        // Відображення статистики
        System.out.println("Статистика виконаних операцій:");
        System.out.println("Кількість чисел у файлі: " + readNumbersFromFile(inputFile).size());
        System.out.println("Кількість простих чисел: " + readNumbersFromFile(primeNumbersFile).size());
        System.out.println("Кількість факторіалів: " + readNumbersFromFile(factorialsFile).size());
        out.println("\n_________________________________\n");


        out.println("Завдання 3:");
        //Scanner scanner2 = new Scanner(System.in);
        System.out.println("Введіть шлях до існуючої директорії: ");
        String sourceDirPath = scanner.nextLine();
        System.out.println("Введіть шлях до нової директорії: ");
        String destDirPath = scanner.nextLine();

        File sourceDir = new File(sourceDirPath);
        File destDir = new File(destDirPath);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.out.println("Існуюча директорія не знайдена або це не директорія.");
            return;
        }

        // Запускаємо потік для копіювання директорії
        Thread copyThread = new Thread(() -> {
            try {
                copyDirectory(sourceDir.toPath(), destDir.toPath());
                System.out.println("Директорія успішно скопійована.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        long startTime = System.currentTimeMillis();

        copyThread.start();
        copyThread.join();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Відображення статистики
        System.out.println("Кількість файлів скопійовано: " + countFiles(destDir));
        System.out.println("Час виконання: " + duration + " мс");*/

        out.println("\n_________________________________\n");


        out.println("Завдання 4:");

        System.out.println("Введіть шлях до директорії: ");
        String dirPath = scanner.nextLine();
        System.out.println("Введіть слово для пошуку: ");
        String searchWord = scanner.nextLine();
        System.out.println("Введіть шлях до файлу із забороненими словами: ");
        String forbiddenWordsFilePath = scanner.nextLine();

        File dir = new File(dirPath);
        File resultFile = new File("src/folder4/result.txt");
        File filteredFile = new File("src/folder4/filtered_result.txt");
        File forbiddenWordsFile = new File(forbiddenWordsFilePath);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Директорія не знайдена або це не директорія.");
            return;
        }

        CountDownLatch latch4 = new CountDownLatch(1);

        // Потік для пошуку файлів і злиття їх вмісту
        Thread searchAndMergeThread = new Thread(() -> {
            try (PrintWriter writer = new PrintWriter(new FileWriter(resultFile))) {
                List<File> filesContainingWord = findFilesContainingWord(dir, searchWord);
                for (File file : filesContainingWord) {
                    List<String> lines = Files.readAllLines(file.toPath());
                    for (String line : lines) {
                        if (line.contains(searchWord)) {
                            writer.println(line);
                        }
                    }
                }
                System.out.println("Файли знайдені та їх вміст злитий у result.txt.");
                latch4.countDown();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Потік для вирізання заборонених слів
        Thread filterForbiddenWordsThread = new Thread(() -> {
            try {
                latch4.await();
                List<String> forbiddenWords = Files.readAllLines(forbiddenWordsFile.toPath());
                List<String> lines = Files.readAllLines(resultFile.toPath());

                List<String> filteredLines = new ArrayList<>();
                for (String line : lines) {
                    StringBuilder filteredLine = new StringBuilder(line);
                    for (String word : forbiddenWords) {
                        // Заміна забороненого слова на зірочки
                        int index = filteredLine.indexOf(word);
                        while (index != -1) {
                            filteredLine.replace(index, index + word.length(), "*".repeat(word.length()));
                            index = filteredLine.indexOf(word, index + word.length());
                        }
                    }
                    filteredLines.add(filteredLine.toString());
                }

                Files.write(filteredFile.toPath(), filteredLines);
                System.out.println("Заборонені слова замінені на зірочки у filtered_result.txt.");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        long startTime = System.currentTimeMillis();

        searchAndMergeThread.start();
        filterForbiddenWordsThread.start();

        searchAndMergeThread.join();
        filterForbiddenWordsThread.join();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Відображення статистики
        System.out.println("Статистика виконаних операцій:");
        System.out.println("Кількість знайдених файлів: " + findFilesContainingWord(dir, searchWord).size());
        System.out.println("Кількість рядків в result.txt: " + Files.readAllLines(resultFile.toPath()).size());
        System.out.println("Кількість рядків в filtered_result.txt: " + Files.readAllLines(filteredFile.toPath()).size());
        System.out.println("Час виконання: " + duration + " мс");

    }
    //Для завдання № 2
    private static List<Integer> readNumbersFromFile(File file) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            }
        }
        return numbers;
    }

    private static List<Long> readLongNumbersFromFile(File file) throws IOException {
        List<Long> numbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLong()) {
                numbers.add(scanner.nextLong());
            }
        }
        return numbers;
    }

    private static <T> void writeNumbersToFile(File file, List<T> numbers) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (T num : numbers) {
                writer.println(num);
            }
        }
    }

    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private static long factorial(int num) {
        if (num == 0) return 1;
        long result = 1;
        for (int i = 1; i <= num; i++) {
            result *= i;
        }
        return result;
    }

    //Для завдання 3
    private static void copyDirectory(Path source, Path dest) throws IOException {
        Files.walk(source).forEach(sourcePath -> {
            Path targetPath = dest.resolve(source.relativize(sourcePath));
            try {
                if (Files.isDirectory(sourcePath)) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static int countFiles(File directory) {
        int count = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    count++;
                } else if (file.isDirectory()) {
                    count += countFiles(file);
                }
            }
        }
        return count;
    }

    //Для завдання 4
    private static List<File> findFilesContainingWord(File dir, String word) {
        List<File> result = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        for (String line : lines) {
                            if (line.contains(word)) {
                                result.add(file);
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (file.isDirectory()) {
                    result.addAll(findFilesContainingWord(file, word));
                }
            }
        }
        return result;
    }
}