
import java.util.Calendar;

public class CuentaBancaria {
    protected int numero;
    protected double saldo;
    protected String cliente;
    protected String moneda;
    protected TipoCuenta tipo;
    protected Calendar apertura;
    protected String[] historial = new String[50];
    protected int cantidadMovimientos = 0;

    public CuentaBancaria(int numero, String cliente, String moneda) {
        this.numero = numero;
        this.cliente = cliente;
        this.moneda = moneda;
        this.saldo = 0;
        this.apertura = Calendar.getInstance();
    }

    public CuentaBancaria(int numero, String cliente, String moneda, TipoCuenta tipo) {
        this(numero, cliente, moneda);
        this.tipo = tipo;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        saldo += monto;
        registrarMovimiento("Deposito + " + monto + " -> saldo: " + saldo);
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a retirar debe ser mayor a cero.");
        }
        if (monto > saldo) {
            return false;
        }
        saldo -= monto;
        registrarMovimiento("Retiro - " + monto + " -> saldo: " + saldo);
        return true;
    }

    public void reintegrar(double monto) {
        saldo += monto;
        registrarMovimiento("Reintegro + " + monto + " -> saldo: " + saldo);
    }

    public void registrarIntereses() {
        saldo += (saldo * tipo.tasa());
    }

    public void quienSoy() {
        System.out.println("CuentaBancaria (clase base)");
    }

    public void registrarMovimiento(String linea) {
        try {
            if (cantidadMovimientos >= historial.length) {
                throw new IllegalStateException("Historial lleno, no se puede registrar el movimiento.");
            }
            historial[cantidadMovimientos++] = linea;
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void imprimirHistorial(int id) {
        if (id >= cantidadMovimientos) {
            return;
        }
        System.out.println(historial[id]);
        imprimirHistorial(id + 1);
    }

    public void imprimirHistorial() {
        imprimirHistorial(0);
    }

    public String toString() {
        return String.format("Cuenta #%d | Cliente: %s | Saldo: %.2f %s | Tipo: %s | Apertura: %s",
                numero, cliente, saldo, moneda, tipo, apertura.getTime());
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }
}
