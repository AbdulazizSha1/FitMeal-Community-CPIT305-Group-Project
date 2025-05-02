package org.example.networking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ChatHandler {
    private static final Set<String> BAD_WORDS = new HashSet<>(Arrays.asList(
            "stupid", "idiot", "dumb", "bad", "shit", "hate"
    ));

    public static void startChat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Chat Started (type 'exit' to quit) ===");

        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                System.out.println("Exiting chat. Goodbye!");
                break;
            }

            String filtered = filterBadWords(userInput);
            System.out.println("Filtered: " + filtered);
        }
    }

    public static String filterBadWords(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (BAD_WORDS.contains(word.toLowerCase())) {
                result.append("**** ");
            } else {
                result.append(word).append(" ");
            }
        }

        return result.toString().trim();
    }
}



