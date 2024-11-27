class Mythread implements Runnable {
    public void run(){
        System.out.println("this thread is running");
    }
}
class MyThreadEx2 {
    public static void main(String[] args) {
        Thread t = new Thread(new MyThread());
        t.start();
    }
}