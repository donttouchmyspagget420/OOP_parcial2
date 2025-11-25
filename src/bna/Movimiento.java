package bna;

import java.util.LinkedList;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Movimiento extends Boleta {
  private String funcion;

  public static enum Funciones {
    REGISTRAR("crear una cuenta nueva"), LOGIN("cambiar de cuenta"), CAMBIAR_CONTRASENIA("cambiar la contraseña"),
    DEPOSITAR("depositar dinero"), RETIRAR("retirar dinero"), TRANSFERIR("transferir dinero a otra cuenta"),
    HISTORIA("ver el historial"), ELIMINAR("eliminar la cuenta"), QUITAR("salir");

    private String descripcion;

    private Funciones(String descripcion) {
      this.descripcion = descripcion;
    }

    public String getFuncion() {
      return descripcion;
    }
  }

  static void registrar(LinkedList<Cuenta> lista) {
    do {
      try {

        String nombre = JOptionPane.showInputDialog("Como es tu nombre?");
        int pin = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su pin"));
        double dinero = 0;

        Cuenta.setActivo(new Cuenta(lista.size(), nombre, pin, dinero));
      } catch (Exception e) {
        Cuenta.setActivo(null);
        continue;
      }
    } while (Cuenta.getActivo() == null);

    JOptionPane.showMessageDialog(null, "Cuenta creada exitosamente", "informacion", JOptionPane.INFORMATION_MESSAGE);
    JOptionPane.showMessageDialog(null, Cuenta.getActivo().toString(), "informacion", JOptionPane.INFORMATION_MESSAGE);

    lista.add(Cuenta.getActivo());
  }

  static void login(LinkedList<Cuenta> lista) {
    if (lista.size() <= 1 && Cuenta.getActivo() != null) {
      JOptionPane.showMessageDialog(null, "no hay cuentas disponibles", "!",
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    boolean corriendo = true;
    int id = -1;
    String nombre;

    while (corriendo) {
      try {
        id = Integer.parseInt(JOptionPane.showInputDialog("cual es tu id?")) - 1;
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "ese ID no existe", "!", JOptionPane.WARNING_MESSAGE);
        continue;
      }
      corriendo = false;
    }
    corriendo = true;

    while (corriendo) {
      nombre = JOptionPane.showInputDialog("como te llamas?");
      if (lista.get(id).getNombre().equalsIgnoreCase(nombre)) {
        corriendo = false;
      }
    }

    lista.get(id).chequiar_pin();

    Cuenta.setActivo(lista.get(id));
  }

  static void depositar() throws Exception {
    Cuenta.getActivo().chequiar_pin();
    double monto = 0;
    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto quieres depositar?"));

        Cuenta.getActivo().setDinero(Cuenta.getActivo().getDinero() + monto);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede depositar " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);

    Movimiento movimiento = new Movimiento(Funciones.DEPOSITAR, Cuenta.getActivo(), monto);
    Cuenta.getActivo().agregarHistoria(movimiento.toString());
  }

  static void retirar() throws Exception {
    Cuenta.getActivo().chequiar_pin();
    double monto = 0;
    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto queres retirar?"));

        Cuenta.getActivo().setDinero(Cuenta.getActivo().getDinero() - monto);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede retirar " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);

    Movimiento movimiento = new Movimiento(Funciones.RETIRAR, Cuenta.getActivo(), monto);
    Cuenta.getActivo().agregarHistoria(movimiento.toString());
  }

  static void transferir(LinkedList<Cuenta> lista) throws Exception {
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
          "A quien queres transferir el dinero?(1-" + lista.size() + ")"));
      if (choice < 0 || choice > lista.size()) {
        JOptionPane.showMessageDialog(null, "Este usuario no existe", "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (choice < 0 || choice > lista.size());
    choice--;

    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto queres transferir?"));

        if (monto > Cuenta.getActivo().getDinero()) {
          throw new Exception();
        }

        Cuenta.getActivo().setDinero(Cuenta.getActivo().getDinero() - monto);
        lista.get(choice).setDinero(lista.get(choice).getDinero() + monto);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede tranferir " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0);

    Movimiento movimiento = new Movimiento(Funciones.TRANSFERIR, Cuenta.getActivo(), lista.get(choice), monto);
    Cuenta.getActivo().agregarHistoria(movimiento.toString());
    lista.get(choice).agregarHistoria(movimiento.toString());

    JOptionPane.showMessageDialog(null, Cuenta.getActivo(), "", JOptionPane.INFORMATION_MESSAGE);
    JOptionPane.showMessageDialog(null, lista.get(choice), "", JOptionPane.INFORMATION_MESSAGE);
  }

  static void eliminar(LinkedList<Cuenta> lista) {
    Cuenta.getActivo().chequiar_pin();
    for (int i = 0; i < lista.size(); i++) {
      if (lista.get(i).getId() == Cuenta.getActivo().getId()) {
        lista.remove(i);
        break;
      }
    }
    Cuenta.setActivo(null);
  }

  static void cambiarContrasenia() {
    Cuenta.getActivo().chequiar_pin();
    int pin = 0;
    do {
      try {
        pin = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su nuevo pin"));
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "su pin TIENE que ser 4 numeros", "!", JOptionPane.WARNING_MESSAGE);
      }
    } while (pin < 0 || pin > 9999 || pin <= 999);
  }

  private Movimiento(Funciones func, Cuenta cuenta, double dinero) throws Exception {
    setRemitente(cuenta.getNombre());
    setBeneficiario(cuenta.getNombre());
    setDinero(dinero);
    setFecha();
    this.funcion = func.getFuncion();
  }

  private Movimiento(Funciones func, Cuenta remitente, Cuenta beneficiario, double dinero) throws Exception {
    setRemitente(remitente.getNombre());
    setBeneficiario(beneficiario.getNombre());
    setDinero(dinero);
    setFecha();
    this.funcion = func.getFuncion();
  }

  static void historia() {
    Cuenta.getActivo().chequiar_pin();
    ArrayList<String> historia = Cuenta.getActivo().getHistoria();

    for (String boleta : historia) {
      JOptionPane.showMessageDialog(null, boleta, "!", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  static boolean quitar() {
    return false;
  }

  @Override
  public String toString() {
    return "remitente: " + getRemitente() + "\n" +
        "beneficiario: " + getBeneficiario() + "\n" +
        "dinero: " + getDinero() + "\n" +
        "fecha: " + getFecha() + "\n" +
        "accion: " + funcion + "\n";
  }

}
