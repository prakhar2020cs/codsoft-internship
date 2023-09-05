import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputText = getInputText(scanner);

        if (inputText.isEmpty()) {
            System.out.println("No text provided. Exiting.");
            return;
        }

        String[] words = tokenizeText(inputText);
        int totalWordCount = words.length;

        System.out.println("Total word count: " + totalWordCount);

        // Option to ignore common stop words and display unique word count
        boolean ignoreStopWords = askToIgnoreStopWords(scanner);
        if (ignoreStopWords) {
            Set<String> stopWords = createStopWordsSet();
            List<String> uniqueWords = filterStopWordsAndGetUniqueWords(words, stopWords);
            int uniqueWordCount = uniqueWords.size();

            System.out.println("Total unique word count: " + uniqueWordCount);
        }

        // Option to display word frequency statistics
        boolean showWordFrequency = askToShowWordFrequency(scanner);
        if (showWordFrequency) {
            Map<String, Integer> wordFrequency = getWordFrequency(words);
            displayWordFrequency(wordFrequency);
        }

        scanner.close();
    }

    private static String getInputText(Scanner scanner) {
        System.out.print("Enter a text or provide a file path: ");
        String input = scanner.nextLine().trim();

        if (input.startsWith("file:")) {
            String filePath = input.substring(5);
            return readTextFromFile(filePath);
        } else {
            return input;
        }
    }

    private static String readTextFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);
            StringBuilder text = new StringBuilder();

            while (fileScanner.hasNextLine()) {
                text.append(fileScanner.nextLine()).append("\n");
            }

            fileScanner.close();
            return text.toString();
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return "";
        }
    }

    private static String[] tokenizeText(String inputText) {
        // Split the text into words using space or punctuation as delimiters
        return inputText.split("[\\s\\p{Punct}]+");
    }

    private static boolean askToIgnoreStopWords(Scanner scanner) {
        System.out.print("Ignore common stop words? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("yes");
    }

    private static Set<String> createStopWordsSet() {
        // Create a set of common English stop words (you can extend this list)
        String[] stopWordsArray = {"a", "an", "the", "in", "on", "at", "to", "and", "is", "of"};
        return new HashSet<>(Arrays.asList(stopWordsArray));
    }

    private static List<String> filterStopWordsAndGetUniqueWords(String[] words, Set<String> stopWords) {
        List<String> uniqueWords = new ArrayList<>();
        for (String word : words) {
            String lowerCaseWord = word.toLowerCase();
            if (!stopWords.contains(lowerCaseWord) && !uniqueWords.contains(lowerCaseWord)) {
                uniqueWords.add(lowerCaseWord);
            }
        }
        return uniqueWords;
    }

    private static boolean askToShowWordFrequency(Scanner scanner) {
        System.out.print("Show word frequency statistics? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("yes");
    }

    private static Map<String, Integer> getWordFrequency(String[] words) {
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            String lowerCaseWord = word.toLowerCase();
            wordFrequency.put(lowerCaseWord, wordFrequency.getOrDefault(lowerCaseWord, 0) + 1);
        }
        return wordFrequency;
    }

    private static void displayWordFrequency(Map<String, Integer> wordFrequency) {
        System.out.println("Word Frequency Statistics:");
        for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
