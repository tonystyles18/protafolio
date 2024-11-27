abstract public class Sujeto {
    protected Observador[] observan;
    protected int pos; // Variable para controlar la posición actual

    public Sujeto() {
        observan = new Observador[10];
        pos = 0; 
    }

    abstract public Observador adscribir(Observador e);
    abstract public Observador quitar(Observador e);
    abstract public void notificar(); // Método para notificar a los observadores
}

public class Sensor extends Sujeto {
    private double valor;

    public Sensor() {}

    public double getValor() {
        return valor;
    }

    public Observador adscribir(Observador e) {
        if (pos < observan.length) { 
            observan[pos] = e;
            pos++;
            return e;
        } else {
            System.out.println("No se pueden agregar más observadores.");
            return null; 
        }
    }

    public Observador quitar(Observador e) {
        for (int j = 0; j < observan.length; j++) { 
            if (observan[j] != null && observan[j].equals(e)) {
                observan[j] = null;
                // Compactar el array:
                for (int k = j; k < pos - 1; k++) {
                    observan[k] = observan[k + 1];
                }
                observan[pos - 1] = null; 
                pos--;
                return e;
            }
        }
        System.out.println("No se encontró el elemento");
        return null;
    }

    public void notificar() { 
        for (int j = 0; j < pos; j++) {
            if (observan[j] != null) {
                observan[j].actualizar(this); // Asumiendo que 'actualizar' es el método en Observador
            }
        }
    }

    // Método para cambiar el valor y notificar a los observadores
    public void setValor(double nuevoValor) {
        this.valor = nuevoValor;
        notificar();
    }
}