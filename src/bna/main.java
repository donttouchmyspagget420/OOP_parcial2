package bna;

import java.util.LinkedList;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class main {

  public static void main(String[] args) {
    Cuenta cuenta = null;
    String[] opciones = { "depositar", "retirar", "transferir", "Ver la historia", "eliminar cuenta",
        "crear nueva cuenta",
        "ingresar a tu cuenta",
        "quitar" };
    int opcion;
    boolean corriendo = true;

    JOptionPane.showMessageDialog(null, "Banco de la nacion Argentina", "bna",
        JOptionPane.INFORMATION_MESSAGE);

    do {
      if (cuenta != null) {
        opciones[6] = "cambiar cuenta";
      } else {
        opciones[6] = "ingresar a tu cuenta";
      }

      opcion = JOptionPane.showOptionDialog(null, (!(cuenta == null)) ? cuenta.toString() : "",
          "eliga un opcion",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
          opciones[0]);

      if (cuenta == null && opcion <= 4) {
        JOptionPane.showMessageDialog(null, "no lograste ingresar a la cuenta", "!",
            JOptionPane.WARNING_MESSAGE);
        continue;
      }

      switch (opcion) {
        case 0 -> depositar(cuenta);
        case 1 -> retirar(cuenta);
        case 2 -> transferir(cuenta, Cuenta.getLista());
        case 3 -> historia(cuenta);
        case 4 -> cuenta = eliminar(cuenta, Cuenta.getLista());
        case 5 -> cuenta = registrar(Cuenta.getLista(), cuenta);
        case 6 -> cuenta = login(Cuenta.getLista(), cuenta);
        case 7 -> corriendo = false;
      }

    } while (corriendo);
  }
}
