import java.util.*;

public class Parser {

    static class Grammar {
        Set<Character> alphabet = new HashSet<>();
        Set<String> states = new HashSet<>();
        String startState;
        Set<String> finalStates = new HashSet<>();
        Map<String, Map<Character, Set<String>>> transitions = new HashMap<>(); 

        // Convierte una expresión regular a un AFN (Algoritmo de Thompson)
        void fromRegex(String regex) {
            Stack<State> stack = new Stack<>();

            for (char c : regex.toCharArray()) {
                switch (c) {
                    case '|':
                        State s2 = stack.pop();
                        State s1 = stack.pop();
                        State newStart = new State();
                        State newEnd = new State();
                        newStart.addTransition(null, s1);
                        newStart.addTransition(null, s2);
                        s1.addTransition(null, newEnd);
                        s2.addTransition(null, newEnd);
                        stack.push(newStart);
                        stack.push(newEnd);
                        break;
                    case '*':
                        State s = stack.pop();
                        State start = new State();
                        State end = new State();
                        start.addTransition(null, s);
                        start.addTransition(null, end);
                        s.addTransition(null, end);
                        s.addTransition(null, s);
                        stack.push(start);
                        stack.push(end);
                        break;
                    case '.':  // Concatenación
                        s2 = stack.pop();
                        s1 = stack.pop();
                        s1.addTransition(null, s2); 
                        stack.push(s1);
                        stack.push(s2);
                        break;
                    default:
                        start = new State();
                        end = new State();
                        start.addTransition(c, end);
                        stack.push(start);
                        stack.push(end);
                }
            }

            State finalState = stack.pop();
            startState = stack.pop();
            finalStates.add(finalState.name);

            // Agregar todos los estados y el alfabeto al conjunto
            Set<State> visited = new HashSet<>();
            Stack<State> stateStack = new Stack<>();
            stateStack.push(startState);
            while (!stateStack.isEmpty()) {
                State currentState = stateStack.pop();
                if (!visited.contains(currentState)) {
                    visited.add(currentState);
                    states.add(currentState.name);
                    for (Map.Entry<Character, Set<State>> transition : currentState.transitions.entrySet()) {
                        if (transition.getKey() != null) { 
                            alphabet.add(transition.getKey());
                        }
                        for (State nextState : transition.getValue()) {
                            stateStack.push(nextState);
                        }
                    }
                }
            }
        }

        // Construye la tabla de transiciones a partir del AFN, incluyendo transiciones epsilon
        void buildTransitionTable() {
            Map<String, Set<String>> epsilonTransitions = new HashMap<>(); 

            // Calcular transiciones epsilon
            for (String state : states) {
                epsilonTransitions.put(state, epsilonClosure(state));
            }

            for (String state : states) {
                transitions.put(state, new HashMap<>());
                for (char symbol : alphabet) {
                    Set<String> nextStates = new HashSet<>();
                    for (String s : epsilonTransitions.get(state)) { 
                        if (transitions.containsKey(s) && transitions.get(s).containsKey(symbol)) {
                            nextStates.addAll(transitions.get(s).get(symbol));
                        }
                    }
                    // Agregar las transiciones epsilon de los estados alcanzados con 'symbol'
                    for (String nextState : nextStates) {
                        nextStates.addAll(epsilonTransitions.get(nextState));
                    }
                    transitions.get(state).put(symbol, nextStates); 
                }
            }
        }

        // Calcula la cerradura epsilon de un estado
        Set<String> epsilonClosure(String state) {
            Set<String> closure = new HashSet<>();
            closure.add(state); 
            Stack<String> stack = new Stack<>();
            stack.push(state);
            while (!stack.isEmpty()) {
                String currentState = stack.pop();
                Set<String> nextStates = transitions.getOrDefault(currentState, new HashMap<>()).getOrDefault(null, new HashSet<>());
                for (String nextState : nextStates) {
                    if (!closure.contains(nextState)) {
                        closure.add(nextState);
                        stack.push(nextState);
                    }
                }
            }
            return closure;
        }

        boolean isValid(String input) {
            Set<String> currentStates = epsilonClosure(startState); // Incluir estados alcanzables por epsilon desde el estado inicial
            for (char c : input.toCharArray()) {
                Set<String> nextStates = new HashSet<>();
                for (String state : currentStates) {
                    if (transitions.containsKey(state) && transitions.get(state).containsKey(c)) {
                        nextStates.addAll(transitions.get(state).get(c));
                    }
                }
                currentStates = nextStates; 
                if (currentStates.isEmpty()) { // Si no hay estados siguientes, la cadena no es válida
                    return false; 
                }
            }
            // Verificar si algún estado final es alcanzable desde los estados actuales a través de transiciones epsilon
            for (String state : currentStates) {
                if (epsilonClosure(state).stream().anyMatch(finalStates::contains)) {
                    return true; 
                }
            }
            return false; 
        }
    }

    static class State {
        static int counter = 0;
        String name;
        Map<Character, Set<State>> transitions = new HashMap<>();

        State() {
            this.name = "q" + counter++;
        }

        void addTransition(Character symbol, State nextState) {
            transitions.putIfAbsent(symbol, new HashSet<>());
            transitions.get(symbol).add(nextState);
        }
    }

    public static void main(String[] args) {
        Grammar grammar = new Grammar();
        grammar.fromRegex("(a|b)*a"); 
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
        scanner.close(); 
    }
}