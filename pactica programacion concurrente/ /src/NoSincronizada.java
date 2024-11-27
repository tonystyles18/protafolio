import java.util.concurrent.ConcurrentLinkedQueue;

public class NoSincronizada {
    static ConcurrentLinkedQueue<Integer> numeros = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 1; i <= 20; i++) {
            numeros.add(i);
        }
    }

    public void run() {
        while (!numeros.isEmpty()) {
            System.out.println(numeros.poll());
        }
    }

    public static void main(String args[]) {
        NoSincronizada Thr1 = new NoSincronizada();
        NoSincronizada Thr2 = new NoSincronizada();
        Thr1.start();
        Thr2.start();
    }
}