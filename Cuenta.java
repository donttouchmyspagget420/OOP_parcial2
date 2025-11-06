
public class Cuenta {
	private static int cantidad_de_clientes = 0;
	private String nombre;
	private int pin, id;
	private double dinero;

	// getters && setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre.isBlank() || nombre == null) {
			return;
		}
		this.nombre = nombre;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		if (pin < 0 && pin > 9999 && pin > 999) {
			System.out.println("pin debe ser 4 números");
			return;
		}
		this.pin = pin;
	}

	public int getId() {
		return id;
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

	// constructor
	public Cuenta(String nombre, int pin, double dinero) {
		cantidad_de_clientes++;
		this.id = cantidad_de_clientes;
		setNombre(nombre);
		setPin(pin);
		setDinero(dinero);
	}

	// toString
	@Override
	public String toString() {
		String str = "id: " + this.id + "\n" +
				"nombre: " + this.nombre + "\n" +
				"dinero disponible: " + this.dinero;
		return str;
	}
}
