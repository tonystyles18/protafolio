public class Main {
    public static void main(String[] args) {
        // Crear una instancia del sensor
        Sensor sensorTemperatura = new Sensor();

        // Crear observadores
        Observador pantalla = new Observador() {
            @Override
            public void actualizar(Sujeto sujeto) {
                Sensor sensor = (Sensor) sujeto;
                System.out.println("Pantalla: Temperatura actualizada: " + sensor.getValor());
            }
        };

        Observador alarma = new Observador() {
            @Override
            public void actualizar(Sujeto sujeto) {
                Sensor sensor = (Sensor) sujeto;
                if (sensor.getValor() > 30) {
                    System.out.println("Alarma: ¡Temperatura crítica!");
                }
            }
        };

        // Adscribir los observadores al sensor
        sensorTemperatura.adscribir(pantalla);
        sensorTemperatura.adscribir(alarma);

        // Cambiar el valor del sensor y observar las notificaciones
        sensorTemperatura.setValor(25);
        sensorTemperatura.setValor(32); 

        // Quitar un observador
        sensorTemperatura.quitar(pantalla);

        // Cambiar el valor de nuevo
        sensorTemperatura.setValor(28); 
    }
}

// Implementación básica de la interfaz Observador
interface Observador {
    void actualizar(Sujeto sujeto);
}