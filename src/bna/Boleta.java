package bna;

import java.time.LocalDate;

public class Boleta {

  private String remitente, beneficiario;
  private double dinero;
  private LocalDate fecha;

  public Boleta(String remitente, String beneficiario, double dinero) {
    this.remitente = remitente;
    this.beneficiario = beneficiario;
    this.dinero = dinero;
    this.fecha = LocalDate.now();
  }

  @Override
  public String toString() {
    return "reminente: " + remitente + "\n" +
        "beneficario: " + beneficiario + "\n" +
        "dinero: " + dinero + "\n" +
        "fecha: " + fecha + "\n";
  }

}
