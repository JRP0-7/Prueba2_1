
public class CuentaCheques extends CuentaBancaria {
    private double limiteSobregiro;

    public CuentaCheques(int numero, String cliente, String moneda, double limiteSobregiro) {
        super(numero, cliente, moneda, TipoCuenta.CHEQUES);
        if (limiteSobregiro < 0) {
            throw new IllegalArgumentException("El límite de sobregiro no puede ser negativo.");
        }
        this.limiteSobregiro = limiteSobregiro;
    }

    public void girarCheque(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del cheque debe ser mayor a cero.");
        }
        if (monto > (saldo + limiteSobregiro)) {
            throw new IllegalStateException("Fondos insuficientes para girar el cheque");
        }
        saldo -= monto;
        registrarMovimiento("Cheque -" + monto + " -> Saldo: " + saldo);
    }

    public void quienSoy() {
        System.out.println("Soy una CuentaCheques");
    }

    public String toString() {
        return "Soy una CuentaCheques  " + super.toString() + "; Sobregiro disponible: " + (limiteSobregiro + saldo);
    }
}