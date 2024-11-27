public class Sincronizada extends Thread {
    static int n = 1;
    static Object lock = new Object(); // Objeto de bloqueo

    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                System.out.println(n);
                n++;
            }
        }
    }

    public static void main(String args[]) {
        Thread thr1 = new Sincronizada();
        Thread thr2 = new Sincronizada();
        thr1.start();
        thr2.start();
    }
}