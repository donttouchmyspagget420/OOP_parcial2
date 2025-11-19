package bna;

public class Cuenta extends Usuario {
  private static Cuenta activo;

  public static Cuenta getActivo() {
    return activo;
  }

  public static void setActivo(Cuenta activo) {
    Cuenta.activo = activo;
  }

  // constructor
  public Cuenta(int size, String nombre, int pin, double dinero) throws Exception {
    setId(size);
    setNombre(nombre);
    setPin(pin);
    setDinero(dinero);
  }

  // toString
  @Override
  public String toString() {
    String str = "id: " + getId() + "\n" +
        "nombre: " + getNombre() + "\n" +
        "pin: " + "****" + "\n" +
        "dinero disponible: " + getDinero() + "\n";
    return str;
  }
}
