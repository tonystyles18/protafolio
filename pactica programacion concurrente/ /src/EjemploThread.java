public class EjemploThread extends Thread {

    int numero;

    EjemploThread(int n) {
        numero = n;
    }

    public void run() {
        try {
            while (numero <= 8) {
                System.out.println(numero);
                sleep((long) (1000 * Math.random()));
                numero++;
            }
        } catch (InterruptedException e) {
            return; // acaba este thread
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < 8; i++) {
            new EjemploThread(i).start();
        }
    }
}