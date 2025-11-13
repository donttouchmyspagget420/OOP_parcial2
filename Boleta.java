import java.time.LocalDate;

public class Boleta {

 private String remitente, beneficario;
 private double dinero;
 private LocalDate fecha;

 public Boleta(String remitente, String beneficario, double dinero) {
  this.remitente = remitente;
  this.beneficario = beneficario;
  this.dinero = dinero;
  this.fecha = LocalDate.now();
 }

 @Override
 public String toString() {
  return "reminente: " + remitente + "\n" +
    "beneficario: " + beneficario + "\n" +
    "dinero: " + dinero + "\n" +
    "fecha: " + fecha + "\n";
 }

}
