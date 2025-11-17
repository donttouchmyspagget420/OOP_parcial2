import java.util.ArrayList;
import java.util.LinkedList;

public class Cuenta {
 private static LinkedList<Cuenta> lista = new LinkedList<>();
 private String nombre;
 private int pin, id;
 private double dinero;
 private ArrayList<Boleta> historia = new ArrayList<>();

 // getters && setters
 public static LinkedList<Cuenta> getLista() {
  return lista;
 }

 public String getNombre() {
  return nombre;
 }

 public void setNombre(String nombre) throws Exception {
  if (nombre.isBlank() || nombre == null) {
   throw new Exception("Ingrese tu nombre");
  }
  this.nombre = nombre;
 }

 public int getPin() {
  return pin;
 }

 public void setPin(int pin) throws Exception {
  if (pin < 0 || pin > 9999 || pin <= 999) {
   throw new Exception("El pin DEBE ser 4 números");
  }
  this.pin = pin;
 }

 public int getId() {
  return id;
 }

 public double getDinero() {
  return dinero;
 }

 public void setDinero(double dinero) throws Exception {
  if (dinero < 0) {
   throw new Exception("no podes tener balance menor a 0");
  }
  this.dinero = dinero;
 }

 public ArrayList<Boleta> getHistoria() {
  return historia;
 }

 public void agregarBoleta(String remitente, String beneficiario, double dinero) {
  historia.add(new Boleta(remitente, beneficiario, dinero));
 }

 // constructor
 public Cuenta(String nombre, int pin, double dinero) throws Exception {
  this.id = lista.size() + 1;
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
