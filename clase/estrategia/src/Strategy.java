public interface Strategy {
    int calcular();
}

public class ConcreteStrategyOne implements Strategy {
    public int calcular() {
        return 1; 
    }
}

public class ConcreteStrategyTwo implements Strategy {
    public int calcular() {
        return 2; 
    }
}

public class ConcreteStrategyThree implements Strategy {
    public int calcular() {
        return 3;
    }
}

public class Context {
    Strategy strategy = null;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int realizarOperacion() {
        return this.strategy.calcular();
    }
}

public class Main {
    public static void main(String[] args) {
        // Creamos el contexto con la estrategia ConcreteStrategyOne
        Context context = new Context(new ConcreteStrategyOne());
        System.out.println("Resultado de la estrategia 1: " + context.realizarOperacion()); // Salida: 1

        // Cambiamos la estrategia a ConcreteStrategyTwo
        context.strategy = new ConcreteStrategyTwo(); 
        System.out.println("Resultado de la estrategia 2: " + context.realizarOperacion()); // Salida: 2

        // Cambiamos la estrategia a ConcreteStrategyThree
        context.strategy = new ConcreteStrategyThree();
        System.out.println("Resultado de la estrategia 3: " + context.realizarOperacion()); // Salida: 3
    }
}