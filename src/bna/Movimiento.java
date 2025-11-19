package bna;

import java.util.LinkedList;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Movimiento extends Boleta {
  private Funciones funcion;

  public static enum Funciones {
    REGISTRAR("crear nueva cuenta"), LOGIN("cambiar cuenta"), CAMBIAR_CONTRASENIA("cambiar la contrasenia"),
    DEPOSITAR("depositar dinero"), RETIRAR("retirar dinero"), TRANSFERIR("transferir dinero a otra cuenta"),
    HISTORIA("ver la historia"), QUITAR("quitar");

    private String descripcion;

    private Funciones(String descripcion) {
      this.descripcion = descripcion;
    }

    public String getFuncion() {
      return descripcion;
    }
  }

  Cuenta registrar(LinkedList<Cuenta> lista, Cuenta cuenta) {
    do {
      try {

        String nombre = JOptionPane.showInputDialog("Como te llamas?");
        int pin = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su pin"));
        double dinero = 0;

        cuenta = new Cuenta(lista.size(), nombre, pin, dinero);
      } catch (Exception e) {
        cuenta = null;
        continue;
      }
    } while (cuenta == null);

    JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente", "informacion", JOptionPane.INFORMATION_MESSAGE);
    JOptionPane.showMessageDialog(null, cuenta.toString(), "informacion", JOptionPane.INFORMATION_MESSAGE);

    lista.add(cuenta);
    return cuenta;
  }

  Cuenta login(LinkedList<Cuenta> lista, Cuenta cuenta) {
    if (lista.size() <= 1 && cuenta != null) {
      JOptionPane.showMessageDialog(null, "no hay cuentas disponibles", "!",
          JOptionPane.WARNING_MESSAGE);
      return cuenta;
    }
    boolean corriendo = true;
    int id = -1;
    String nombre;

    while (corriendo) {
      try {
        id = Integer.parseInt(JOptionPane.showInputDialog("cual es tu id?"));
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "esta id no existe", "!", JOptionPane.WARNING_MESSAGE);
        continue;
      }
      corriendo = false;
    }
    corriendo = true;

    while (corriendo) {
      nombre = JOptionPane.showInputDialog("como te llama?");
      if (lista.get(id).getNombre().equalsIgnoreCase(nombre)) {
        corriendo = false;
      }
    }

    lista.get(id).chequiar_pin();

    return lista.get(id);
  }

  void depositar(Cuenta cuenta) throws Exception {
    cuenta.chequiar_pin();
    double monto = 0;
    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés depositar?"));

        cuenta.setDinero(cuenta.getDinero() + monto);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede depositar " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);

    Movimiento movimiento = new Movimiento(Funciones.DEPOSITAR, cuenta, monto);
    cuenta.agregarHistoria(movimiento.toString());
  }

  void retirar(Cuenta cuenta) throws Exception {
    cuenta.chequiar_pin();
    double monto = 0;
    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés transferir?"));

        cuenta.setDinero(cuenta.getDinero() - monto);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede transferir " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);

    Movimiento movimiento = new Movimiento(Funciones.RETIRAR, cuenta, monto);
    cuenta.agregarHistoria(movimiento.toString());
  }

  void transferir(Cuenta cuenta, LinkedList<Cuenta> lista) throws Exception {
    if (lista.size() < 2) {
      JOptionPane.showMessageDialog(null, "no hay cuentas disponibles", "!",
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    double monto = 0;
    int choice;
    do {
      for (Cuenta c : lista) {
        JOptionPane.showMessageDialog(null, c, "información", JOptionPane.INFORMATION_MESSAGE);
      }
      choice = Integer.valueOf(JOptionPane.showInputDialog(
          "A quien querés transferir el dinero?(1-" + lista.size() + ")"));
      if (choice < 0 || choice > lista.size()) {
        JOptionPane.showMessageDialog(null, "Este usuario no existe", "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (choice < 0 || choice > lista.size());
    choice--;

    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés transferir?"));

        if (monto > cuenta.getDinero()) {
          throw new Exception();
        }

        cuenta.setDinero(cuenta.getDinero() - monto);
        lista.get(choice).setDinero(lista.get(choice).getDinero() + monto);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede tranferir " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);

    Movimiento movimiento = new Movimiento(Funciones.TRANSFERIR, cuenta, lista.get(choice), monto);
    cuenta.agregarHistoria(movimiento.toString());
    lista.get(choice).agregarHistoria(movimiento.toString());

    JOptionPane.showMessageDialog(null, cuenta, "", JOptionPane.INFORMATION_MESSAGE);
    JOptionPane.showMessageDialog(null, lista.get(choice), "", JOptionPane.INFORMATION_MESSAGE);
  }

  Cuenta eliminar(LinkedList<Cuenta> lista, Cuenta cuenta) {
    cuenta.chequiar_pin();
    for (int i = 0; i < lista.size(); i++) {
      if (lista.get(i).getId() == cuenta.getId()) {
        lista.remove(i);
        break;
      }
    }
    return null;
  }

  void cambiarContrasenia(Cuenta cuenta) {
    cuenta.chequiar_pin();
    int pin = 0;
    do {
      try {
        pin = Integer.parseInt(JOptionPane.showInputDialog("Ingrese tu nueve pin"));
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "pin tiene que ser 4 numeros", "!", JOptionPane.WARNING_MESSAGE);
      }
    } while (pin < 0 || pin > 9999 || pin <= 999);
  }

  private Movimiento(Funciones func, Cuenta cuenta, double dinero) throws Exception {
    setRemitente(cuenta.getNombre());
    setBeneficiario(cuenta.getNombre());
    setDinero(dinero);
    setFecha();
    this.funcion = func;
  }

  private Movimiento(Funciones func, Cuenta remitente, Cuenta beneficiario, double dinero) throws Exception {
    setRemitente(remitente.getNombre());
    setBeneficiario(beneficiario.getNombre());
    setDinero(dinero);
    setFecha();
    this.funcion = func;
  }

  void historia(Cuenta cuenta) {
    cuenta.chequiar_pin();
    ArrayList<String> historia = cuenta.getHistoria();

    for (String boleta : historia) {
      JOptionPane.showMessageDialog(null, boleta, "!", JOptionPane.INFORMATION_MESSAGE);
    }
  }

}
