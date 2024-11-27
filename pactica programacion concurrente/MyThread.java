public class MyThread exteds theread {
public void run (){
    System.out.println("this thread is runnig");
}
}
class MyThreadEx1{
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}