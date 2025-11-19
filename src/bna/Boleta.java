package bna;

import java.time.LocalDate;

abstract class Boleta {

  private String remitente, beneficiario;
  private double dinero;
  private LocalDate fecha;

  // getters && setters
  public void setRemitente(String remitente) throws Exception {
    if (remitente.isBlank()) {
      throw new Exception("no puede ser vacio");
    }
    this.remitente = remitente;
  }

  public void setBeneficiario(String beneficiario) throws Exception {
    if (remitente.isBlank()) {
      throw new Exception("no puede ser vacio");
    }
    this.beneficiario = beneficiario;
  }

  public void setDinero(double dinero) throws Exception {
    if (dinero < 0) {
      throw new Exception("no puede ser negativo");
    }
    this.dinero = dinero;
  }

  public void setFecha() {
    this.fecha = LocalDate.now();
  }

  @Override
  public String toString() {
    return "remitente: " + remitente + "\n" +
        "beneficiario: " + beneficiario + "\n" +
        "dinero: " + dinero + "\n" +
        "fecha: " + fecha + "\n";
  }

}
