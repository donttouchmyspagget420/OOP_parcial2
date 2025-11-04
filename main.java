package bna;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class main {

  private static Cuenta registrar(LinkedList<Cuenta> lista) {
    Cuenta cuenta;
    do {
      String nombre = JOptionPane.showInputDialog("Como te llama?");
      int pin = Integer.parseInt(JOptionPane.showInputDialog("Ingrese tu pin"));
      double dinero = 0;

      cuenta = new Cuenta(nombre, pin, dinero);
    } while (!lista.add(cuenta));

    JOptionPane.showMessageDialog(null, "Cuenta creado");
    JOptionPane.showMessageDialog(null, cuenta.toString());

    return cuenta;
  }

  private static Cuenta login(LinkedList<Cuenta> lista) {
    Cuenta cuenta = null;
    boolean bool = true;
    String nombre;
    int pin;

    do {
      nombre = JOptionPane.showInputDialog("Como te llama?");
      for (Cuenta cuenta2 : lista) {
        if (cuenta2.getNombre().equalsIgnoreCase(nombre)) {
          bool = false;
          cuenta = cuenta2;
        }
      }
      if (!bool) {
        JOptionPane.showMessageDialog(null, "Cuenta de " + nombre + " no existe");
      }
    } while (bool);

    do {
      pin = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese tu pin"));
    } while (!(cuenta.getPin() == pin));

    return cuenta;
  }

  private static void depositar(Cuenta cuenta) {
    double monto;
    do {
      monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés depositar?"));
      if (monto < 0) {
        JOptionPane.showMessageDialog(null, "no se puede depositar " + monto, "error", JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);
    cuenta.setDinero(cuenta.getDinero() + monto);
  }

  private static void retirar(Cuenta cuenta) {
    double monto;
    do {
      monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés retirar?"));
      if (monto > cuenta.getDinero()) {
        JOptionPane.showMessageDialog(null, "no tenés sufficiente dinero", "error", JOptionPane.WARNING_MESSAGE);
      } else if (monto < 0) {
        JOptionPane.showMessageDialog(null, "no se puede retirar " + monto, "error", JOptionPane.WARNING_MESSAGE);
      }
    } while (monto > cuenta.getDinero() || monto < 0);
    cuenta.setDinero(cuenta.getDinero() - monto);
  }

  private static void transferir(Cuenta cuenta, LinkedList<Cuenta> lista) {
    double dinero;
    int choice;
    do {
      for (Cuenta c : lista) {
        JOptionPane.showMessageDialog(null, c, "información", JOptionPane.INFORMATION_MESSAGE);
      }
      choice = Integer.valueOf(JOptionPane.showInputDialog("A quien querés transferir el dinero?"));
      if (choice < 0 || choice > lista.size()) {
        JOptionPane.showMessageDialog(null, "no hay este usuario", "error", JOptionPane.WARNING_MESSAGE);
      }
    } while (choice < 0 || choice > lista.size());
    Cuenta cuenta2 = lista.get(choice + 1);

    do {
      dinero = Double.valueOf(JOptionPane.showInputDialog("cuanto querés transferir?"));
      if (dinero > cuenta.getDinero()) {
        JOptionPane.showMessageDialog(null, "no tenés sufficiente dinero", "error", JOptionPane.WARNING_MESSAGE);
      } else if (dinero < 0) {
        JOptionPane.showMessageDialog(null, "no se puede transferir " + dinero, "error", JOptionPane.WARNING_MESSAGE);
      }
    } while (dinero > cuenta.getDinero() || dinero < 0);

    cuenta.setDinero(cuenta.getDinero() - dinero);
    cuenta2.setDinero(cuenta2.getDinero() + dinero);

    System.out.println(lista.get(choice));
  }

  private static void eliminar(Cuenta cuenta, LinkedList<Cuenta> lista) {
    for (int i = 0; i < lista.size(); i++) {
      if (lista.get(i).getId() == cuenta.getId()) {
        lista.remove(i);
        break;
      }

    }
    cuenta = null;
  }

  public static void main(String[] args) {
    LinkedList<Cuenta> lista = new LinkedList<>();
    Cuenta cuenta = null;
    String[] opciones = { "depositar", "retirar", "transferir", "registrar", "login", "eliminar", "quitar" };
    int eligo;
    boolean corriendo = true;

    JOptionPane.showMessageDialog(null, "Banco de la nacion Argentina");

    do {
      eligo = JOptionPane.showOptionDialog(null, (!(cuenta == null)) ? cuenta.toString() : "", "eliga un opcion",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
          opciones[0]);

      switch (eligo) {
        case 3 -> cuenta = registrar(lista);
        case 4 -> cuenta = login(lista);
      }

      if (!(cuenta == null)) {
        switch (eligo) {
          case 0 -> depositar(cuenta);
          case 1 -> retirar(cuenta);
          case 2 -> transferir(cuenta, lista);
          case 5 -> eliminar(cuenta, lista);
          case 6 -> corriendo = false;

        }
      } else {
        JOptionPane.showMessageDialog(null, "no entraste a su cuenta");
      }
    } while (corriendo);
  }
}
