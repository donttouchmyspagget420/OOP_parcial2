import java.util.LinkedList;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class main {

	private static void chequiar_pin(Cuenta cuenta) {
		int pin = 0;
		do {
			try {
				pin = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese tu pin"));
				if (pin < 0 || pin > 9999) {
					throw new Exception();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "pin no es correcto");
			}
		} while (!(cuenta.getPin() == pin));
	}

	private static Cuenta registrar(LinkedList<Cuenta> lista) {
		Cuenta cuenta = null;
		do {
			try {

				String nombre = JOptionPane.showInputDialog("Como te llama?");
				int pin = Integer.parseInt(JOptionPane.showInputDialog("Ingrese tu pin"));
				double dinero = 0;

				cuenta = new Cuenta(nombre, pin, dinero);
			} catch (Exception e) {
				cuenta = null;
				continue;
			}
		} while (cuenta == null);

		JOptionPane.showMessageDialog(null, "Cuenta creado", "informacion", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, cuenta.toString(), "informacion", JOptionPane.INFORMATION_MESSAGE);

		return cuenta;
	}

	private static Cuenta login(LinkedList<Cuenta> lista) {
		Cuenta cuenta = null;
		boolean bool = true;
		String nombre;

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

		chequiar_pin(cuenta);

		return cuenta;
	}

	private static void depositar(Cuenta cuenta) {
		chequiar_pin(cuenta);
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
	}

	private static void retirar(Cuenta cuenta) {
		chequiar_pin(cuenta);
		double monto = 0;
		do {
			try {
				monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés retirar?"));

				cuenta.setDinero(cuenta.getDinero() - monto);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "no se puede retirar " + monto, "error",
						JOptionPane.WARNING_MESSAGE);
			}
		} while (monto <= 0);
	}

	private static void transferir(Cuenta cuenta, LinkedList<Cuenta> lista) {
		double monto = 0;
		int choice;
		do {
			for (Cuenta c : lista) {
				JOptionPane.showMessageDialog(null, c, "información", JOptionPane.INFORMATION_MESSAGE);
			}
			choice = Integer.valueOf(JOptionPane.showInputDialog(
					"A quien querés transferir el dinero?(1-" + lista.size() + ")"));
			if (choice < 0 || choice > lista.size()) {
				JOptionPane.showMessageDialog(null, "no hay este usuario", "error",
						JOptionPane.WARNING_MESSAGE);
			}
		} while (choice < 0 || choice > lista.size());
		choice--;

		do {
			try {
				monto = Double.valueOf(JOptionPane.showInputDialog("cuanto querés retirar?"));

				if (monto > cuenta.getDinero()) {
					throw new Exception();
				}

				cuenta.setDinero(cuenta.getDinero() - monto);
				lista.get(choice).setDinero(lista.get(choice).getDinero() + monto);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "no se puede retirar " + monto, "error",
						JOptionPane.WARNING_MESSAGE);
			}
		} while (monto <= 0);

		cuenta.agregarBoleta(cuenta.getNombre(), lista.get(choice).getNombre(), monto);
		lista.get(choice).agregarBoleta(cuenta.getNombre(), lista.get(choice).getNombre(), monto);

		JOptionPane.showMessageDialog(null, cuenta, "", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(null, lista.get(choice), "", JOptionPane.INFORMATION_MESSAGE);
	}

	private static Cuenta eliminar(Cuenta cuenta, LinkedList<Cuenta> lista) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId() == cuenta.getId()) {
				lista.remove(i);
				break;
			}

		}
		return null;
	}

	private static void historia(Cuenta cuenta) {
		ArrayList<Boleta> historia = cuenta.getHistoria();

		for (Boleta boleta : historia) {
			JOptionPane.showMessageDialog(null, boleta, "!", JOptionPane.INFORMATION_MESSAGE);

		}
	}

	public static void main(String[] args) {
		LinkedList<Cuenta> lista = new LinkedList<>();
		Cuenta cuenta = null;
		String[] opciones = { "depositar", "retirar", "transferir", "Ver la historia", "crear nuevo cuenta",
				"ingresar a cuenta",
				"eliminar cuenta",
				"quitar" };
		int eligo;
		boolean corriendo = true;

		JOptionPane.showMessageDialog(null, "Banco de la nacion Argentina", "bna",
				JOptionPane.INFORMATION_MESSAGE);

		do {
			if (cuenta != null) {
				opciones[4] = "cambiar cuenta";
			} else {
				opciones[4] = "ingresar a cuenta";
			}

			eligo = JOptionPane.showOptionDialog(null, (!(cuenta == null)) ? cuenta.toString() : "",
					"eliga un opcion",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
					opciones[0]);

			if (cuenta == null && eligo <= 3) {
				JOptionPane.showMessageDialog(null, "no entraste a su cuenta", "!",
						JOptionPane.WARNING_MESSAGE);
				continue;
			}

			switch (eligo) {
				case 0 -> depositar(cuenta);
				case 1 -> retirar(cuenta);
				case 2 -> transferir(cuenta, lista);
				case 3 -> historia(cuenta);
				case 4 -> cuenta = registrar(lista);
				case 5 -> cuenta = login(lista);
				case 6 -> cuenta = eliminar(cuenta, lista);
				case 7 -> corriendo = false;
			}

		} while (corriendo);
	}
}
