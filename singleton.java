public final class singleton {
 private final static singleton singleton = new singleton();

 private singleton (){
    System.err.println("objeto singleton creado.");
 }

 public static singleton 
 obtenerInstanciaSingleton()
 {return singleton;}
};