package bna;

import java.util.LinkedList;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class main {

  public static void main(String[] args) throws Exception {
    LinkedList<Cuenta> lista = new LinkedList<>();
    Movimiento.Funciones[] funciones = Movimiento.Funciones.values();
    String[] opciones = new String[funciones.length];
    int opcion;
    boolean corriendo = true;

    JOptionPane.showMessageDialog(null, "Banco de la nacion Argentina", "bna",
        JOptionPane.INFORMATION_MESSAGE);

    for (int i = 0; i < opciones.length; i++) {
      opciones[i] = funciones[i].getFuncion();
    }

    do {
      if (Cuenta.getActivo() != null) {
        opciones[1] = "cambiar cuenta";
      } else {
        opciones[1] = "ingresar a tu cuenta";
      }

      opcion = JOptionPane.showOptionDialog(null, (!(Cuenta.getActivo() == null)) ? Cuenta.getActivo().toString() : "",
          "eliga un opcion",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
          opciones[0]);

      if (Cuenta.getActivo() == null && opcion >= 2) {
        JOptionPane.showMessageDialog(null, "no lograste ingresar a la cuenta", "!",
            JOptionPane.WARNING_MESSAGE);
        continue;
      }

      Movimiento.registrar(lista);

      switch (opcion) {
        case 0 -> Movimiento.registrar(lista);
        case 1 -> Movimiento.login(lista);
        case 2 -> Movimiento.cambiarContrasenia();
        case 3 -> Movimiento.depositar();
        case 4 -> Movimiento.retirar();
        case 5 -> Movimiento.transferir(lista);
        case 6 -> Movimiento.historia();
        case 7 -> corriendo = Movimiento.quitar();
      }

    } while (corriendo);
  }
}
