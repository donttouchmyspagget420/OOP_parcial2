package bna;

public class cuentaInversion extends Cuenta {
    private double tasaInteres;

    public cuentaInversion(int size, String nombre, int pin, double dinero, double tasaInteres) throws Exception {
        super(size, nombre, pin, dinero);  
        this.tasaInteres = tasaInteres;
    }

    public double calcularInteresMensual() {
        return getDinero() * tasaInteres / 100;
    }

    public void aplicarInteresMensual() throws Exception {
        double interes = calcularInteresMensual();
        setDinero(getDinero() + interes);
        agregarHistoria("Interés mensual: +" + interes);
    }

    public String mostrarBeneficios() {
        return "Tasa interés: " + tasaInteres + "%\nInterés mensual: " + calcularInteresMensual();
    }
}
