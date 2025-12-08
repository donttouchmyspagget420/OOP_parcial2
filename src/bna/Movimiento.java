package bna;

import java.util.LinkedList;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Movimiento extends Boleta {
  private String funcion;

  public static enum Funciones {
    REGISTRAR("crear cuenta"),
    LOGIN("cambiar cuenta"),
    CAMBIAR_CONTRASENIA("cambiar la contraseña"),
    DEPOSITAR("depositar"),
    RETIRAR("retirar"),
    TRANSFERIR("transferir"),
    HISTORIA("historial"),
    ELIMINAR("eliminar la cuenta"),
    APLICAR_INTERES("aplicar interés mensual"),
    INVERSION_PLAZO_FIJO("inversión a plazo fijo"),
    SALIR("salir");

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

  static void depositar(Cajerx cajerx) throws Exception {
    Cuenta.getActivo().chequiar_pin();
    double monto = 0;
    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto quieres depositar?"));

        if (monto > cajerx.limitoDepositar) {
          JOptionPane.showMessageDialog(null,
              "no se puede depositar " + monto + "\n" + "tenes limito de deposito de: " + cajerx.limitoDepositar,
              "error",
              JOptionPane.WARNING_MESSAGE);
          continue;
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede depositar " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0 || monto > cajerx.limitoDepositar);
    Cuenta.getActivo().setDinero(Cuenta.getActivo().getDinero() + monto);

    cajerx.setTotal(cajerx.getTotal() + monto);

    Movimiento movimiento = new Movimiento(Funciones.DEPOSITAR, Cuenta.getActivo(), monto);
    Cuenta.getActivo().agregarHistoria(movimiento.toString());
  }

  static void retirar(Cajerx cajerx) throws Exception {
    if (cajerx.getTotal() <= 0 || Cuenta.getActivo().getDinero() <= 0) {
      JOptionPane.showMessageDialog(null,
          "no hay dinero en la caja, traiga a la mañana",
          "error",
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    Cuenta.getActivo().chequiar_pin();
    double monto = 0;
    do {
      try {
        monto = Double.valueOf(JOptionPane.showInputDialog("cuanto queres retirar?"));

        if (monto > Cuenta.getActivo().getDinero()) {
          JOptionPane.showMessageDialog(null,
              "no se puede retirar " + monto + "\n" + "no tenes tan dinero",
              "error",
              JOptionPane.WARNING_MESSAGE);
          continue;
        }
        if (monto > cajerx.limitoRetirar) {
          JOptionPane.showMessageDialog(null,
              "no se puede retirar " + monto + "\n" + cajerx.getTotal() + " quedan en la caja",
              "error",
              JOptionPane.WARNING_MESSAGE);
          continue;
        }
        if (monto > cajerx.getTotal()) {
          JOptionPane.showMessageDialog(null,
              "no se puede depositar " + monto + "\n" + "tenes limito de retirar de: " + cajerx.limitoRetirar,
              "error",
              JOptionPane.WARNING_MESSAGE);
          continue;
        }
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "no se puede retirar " + monto, "error",
            JOptionPane.WARNING_MESSAGE);
      }
    } while (monto <= 0 || monto > cajerx.limitoRetirar || monto > cajerx.getTotal()
        || monto > Cuenta.getActivo().getDinero());

    cajerx.setTotal(cajerx.getTotal() - monto);
    Cuenta.getActivo().setDinero(Cuenta.getActivo().getDinero() - monto);

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

  static void aplicarInteresMensual() {
    Cuenta.getActivo().chequiar_pin();

    double tasa = Math.random();

    int rand = Math.round((float) Math.random());

    if (rand == 0) {
      tasa = -tasa;
    }

    double interes = Cuenta.getActivo().getDinero() * tasa / 100;
    double nuevoSaldo = Cuenta.getActivo().getDinero() + interes;

    Cuenta.getActivo().setDinero(nuevoSaldo);
    Cuenta.getActivo().agregarHistoria("Interés mensual " + tasa + "%: +" + interes);

    JOptionPane.showMessageDialog(null,
        "Interés mensual aplicado: " + interes + "\nNuevo saldo: " + nuevoSaldo,
        "Interés Aplicado", JOptionPane.INFORMATION_MESSAGE);

    Movimiento movimiento = null;

    try {
      movimiento = new Movimiento(Funciones.APLICAR_INTERES, Cuenta.getActivo(), nuevoSaldo);
    } catch (Exception e) {
    }
    Cuenta.getActivo().agregarHistoria(movimiento.toString(tasa));
  }

  static void inversionPlazoFijo() {
    Cuenta.getActivo().chequiar_pin();
    double monto = 0;
    double meses = 0;
    double rendimiento = Math.random() * 100;
    double nuevoSaldo = 0;
    do {
      try {
        monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a invertir:"));
        meses = Double.parseDouble(JOptionPane.showInputDialog("Ingrese los meses de la inversión:"));

        if (monto > 0 && monto <= Cuenta.getActivo().getDinero() && meses > 0 && meses <= 12) {
          double ganancia = monto * (rendimiento * meses / 12) / 100;
          nuevoSaldo = Cuenta.getActivo().getDinero() + ganancia;

          Cuenta.getActivo().setDinero(nuevoSaldo);
          Cuenta.getActivo().agregarHistoria("Inversión " + meses + " meses: +" + ganancia);

          JOptionPane.showMessageDialog(null,
              "Inversión realizada exitosamente\nGanancia: " + ganancia +
                  "\nNuevo saldo: " + nuevoSaldo,
              "Inversión Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
          JOptionPane.showMessageDialog(null,
              "Monto inválido o fondos insuficientes",
              "Error", JOptionPane.ERROR_MESSAGE);
        }

      } catch (Exception e) {
        JOptionPane.showMessageDialog(null,
            "Error en la inversión",
            "Error", JOptionPane.ERROR_MESSAGE);
      }
    } while (monto <= 0 || monto > Cuenta.getActivo().getDinero() || meses <= 0 || meses > 12);
    Movimiento movimiento = null;

    try {
      movimiento = new Movimiento(Funciones.INVERSION_PLAZO_FIJO, Cuenta.getActivo(), nuevoSaldo);
    } catch (Exception e) {
    }
    Cuenta.getActivo().agregarHistoria(movimiento.toString(rendimiento));
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

  public String toString(double tasa) {
    return "remitente: " + getRemitente() + "\n" +
        "beneficiario: " + getBeneficiario() + "\n" +
        "dinero: " + getDinero() + "\n" +
        "fecha: " + getFecha() + "\n" +
        "accion: " + funcion + "\n";
  }
}
