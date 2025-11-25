package bna;

import java.util.ArrayList;

import javax.swing.JOptionPane;

abstract class Usuario {
  private String nombre;

  private int pin, id;
  private double dinero;
  private ArrayList<String> historia = new ArrayList<>();

  // getters && setters

  public ArrayList<String> getHistoria() {
    return historia;
  }

  public void agregarHistoria(String historia) {
    this.historia.add(historia);
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) throws Exception {
    if (nombre.isBlank() || nombre == null) {
      throw new Exception("Ingrese su nombre");
    }
    this.nombre = nombre;
  }

  public int getPin() {
    return pin;
  }

  public void setPin(int pin) throws Exception {
    if (pin < 0 || pin > 9999 || pin <= 999) {
      throw new Exception("El pin TIENE que ser 4 números");
    }
    this.pin = pin;
  }

  public int getId() {
    return id;
  }

  public void setId(int size) {
    this.id = size + 1;
  }

  public double getDinero() {
    return dinero;
  }

  public void setDinero(double dinero) throws Exception {
    if (dinero < 0) {
      throw new Exception("no podes tener balance menor a 0");
    }
    this.dinero = dinero;
  }

  public void chequiar_pin() {
    int pin = 0;
    do {
      try {
        pin = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese su pin"));
        if (pin < 0 || pin > 9999 || pin <= 999) {
          throw new Exception();
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "su pin es incorrecto");
      }
    } while (!(this.pin == pin));
  }
}
