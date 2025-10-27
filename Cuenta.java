public class Cuenta {
  private String nombre;
  private int pin;
  private double dinero;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    if (nombre.isBlank()) {
      return;
    }
    this.nombre = nombre;
  }

  public int getPin() {
    return pin;
  }

  public void setPin(int pin) {
    if (pin > 0 && pin < 10000 && pin > 999) {
      System.out.println("no");
      return;
    }
    this.pin = pin;
  }

  public double getDinero() {
    return dinero;
  }

  public void setDinero(double dinero) {
    if (dinero <= 0) {
      return;
    }
    this.dinero = dinero;
  }
}
