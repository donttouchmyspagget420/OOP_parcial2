package bna;

import java.util.LinkedList;

import javax.swing.JOptionPane;

public class main {

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

  private enum OPCIONES {
    depositar, retirar, transferir;
  }

  public static void main(String[] args) {
    LinkedList<Cuenta> lista = new LinkedList<>();
    lista.add(new Cuenta("Madi", 6969, 100));
    lista.add(new Cuenta("Bruh", 6969, 100));
    lista.add(new Cuenta("Pablo", 6969, 100));

    transferir(lista.getFirst(), lista);
  }
}
