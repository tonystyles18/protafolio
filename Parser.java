import java.util.*;

public class Parser {

    static class Grammar {
        Set<Character> alphabet = new HashSet<>();
        Set<String> states = new HashSet<>();
        String startState;
        Set<String> finalStates = new HashSet<>();
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>(); // Usamos un Set para manejar transiciones ε

        // Methods for adding states, transitions, etc. (implementation omitted for brevity)

        // Convierte una expresión regular a un AFN (Algoritmo de Thompson)
        void fromRegex(String regex) {
            // Implement the Thompson's construction algorithm here
        }

        // Construye la tabla de transiciones a partir del AFN
        void buildTransitionTable() {
            for (String state : states) {
                transitions.put(state, new HashMap<>());
                for (char symbol : alphabet) {
                    Set<String> nextStates = new HashSet<>();
                    // Combine transitions for the current symbol and ε-transitions
                    nextStates.addAll(transitions.getOrDefault(state, new HashMap<>()).getOrDefault(symbol, new HashSet<>()));
                    for (String nextState : transitions.getOrDefault(state, new HashMap<>()).getOrDefault(null, new HashSet<>())) {
                        nextStates.add(nextState);  // Add ε-transitions
                    }
                    transitions.get(state).put(symbol, nextStates);
                }
            }
        }

        boolean isValid(String input) {
            Set<String> currentStates = new HashSet<>();
            currentStates.add(startState);
            for (char c : input.toCharArray()) {
                Set<String> nextStates = new HashSet<>();
                for (String state : currentStates) {
                    if (transitions.containsKey(state) && transitions.get(state).containsKey(c)) {
                        nextStates.addAll(transitions.get(state).get(c));
                    }
                }
                currentStates = nextStates;
            }
            return currentStates.stream().anyMatch(finalStates::contains);
        }
    }

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.fromRegex("(a|b)*a"); // Example regular expression
        grammar.buildTransitionTable();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Ingrese una cadena (cadena para salir: 'exit'): ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            boolean isValid = grammar.isValid(input);
            System.out.println("La cadena es " + (isValid ? "válida" : "inválida"));
        }
        scanner.close(); // Close the scanner resource
    }
}