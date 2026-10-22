
import java.util.Calendar;

public class CuentaAhorro extends CuentaBancaria {
    private Calendar ultimoUso;

    public CuentaAhorro(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.AHORRO);
        this.ultimoUso = Calendar.getInstance();
    }

    private boolean estaActiva() {
        Calendar ultimo = Calendar.getInstance();
        ultimo.add(Calendar.MONTH, -6);
        return ultimoUso.after(ultimo);
    }

    public void depositar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("La cuenta de ahorro está inactiva");
        }
        super.depositar(monto);
        this.ultimoUso = Calendar.getInstance();
    }

    public boolean retirar(double monto) {
        if (!estaActiva()) {
            throw new IllegalStateException("La cuenta de ahorro está inactiva.");
        }
        if (super.retirar(monto)) {
            this.ultimoUso = Calendar.getInstance();
            return true;
        }else{
            return false;
        }
    }

    public void quienSoy() {
        System.out.println("Soy una CuentaAhorro");
    }

    public String toString() {
        return "Soy una CuentaAhorro " + super.toString() + "   Último uso: " + ultimoUso.getTime();
    }
}
