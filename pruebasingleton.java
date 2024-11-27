public class pruebasingleton {
    public static void main(String[] args) {
        singleton primerSingleton;
        singleton seguSingleton;
        primerSingleton = singleton.obtenerInstanciaSingleton();
        seguSingleton = singleton.obtenerInstanciaSingleton();

        if(primerSingleton==seguSingleton)
        System.err.println("primersingleton y segundo singleton "+"se refieron al mismo objeto singleton");
    }
}
