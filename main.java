package bna;

public class main {

  public enum OPCIONES {
    depositar, retirar, transferir;
  }

  public static void main(String[] args) {
    Cuenta cuenta1 = new Cuenta("Madi", 6969, 0);
    System.out.println(cuenta1);
  }
}
