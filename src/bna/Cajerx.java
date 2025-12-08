package bna;

public class Cajerx {
  private double total;

  final public double limitoDepositar = 50000;
  final public double limitoRetirar = 100000;

  public void setTotal(double total) throws Exception {
    if (total < 0) {
      throw new Exception();
    }
    this.total = total;
  }

  public double getTotal() {
    return total;
  }

  public Cajerx(double total) throws Exception {
    setTotal(total);
  }

}
