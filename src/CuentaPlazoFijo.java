
import java.util.Calendar;

public class CuentaPlazoFijo extends CuentaBancaria {
    private Calendar findePlazo;
    private double intereses = 0;

    public CuentaPlazoFijo(int numero, String cliente, String moneda) {
        super(numero, cliente, moneda, TipoCuenta.PLAZO);
    }

    public void setPlazo(int year, int mes, int dia) {
        try {
            Calendar fecha = Calendar.getInstance();
            fecha.setLenient(false);
            fecha.set(year, mes - 1, dia);
            this.findePlazo = fecha;
        } catch (Exception e) {
            throw new IllegalArgumentException("Fecha de plazo fijo inválida.");
        }
    }

    private boolean estaVencida() {
        if (findePlazo == null)
            return false;
        return !Calendar.getInstance().before(findePlazo);
    }

    public boolean retirar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (!estaVencida()) {
            if (monto > intereses) {
                return false;
            }
            intereses -= monto;
            registrarMovimiento("Retiro Interes -" + monto + " -> saldo: " + saldo);
            return true;
        } else {
            saldo += intereses;
            intereses = 0;
            return super.retirar(monto);
        }
    }

    public void registrarIntereses() {
        if (!estaVencida()) {
            intereses += (saldo * tipo.tasa());
        } else {
            super.registrarIntereses();
        }
    }

    public void reintegrar(double monto) {
        if (!estaVencida()) {
            intereses += monto;
            registrarMovimiento("Reintragracion +" + monto + " -> saldo: " + saldo);
        } else {
            super.reintegrar(monto);
        }
    }

    public void quienSoy() {
        System.out.println("Soy una CuentaPlazoFijo");
    }

    public String toString() {
        String val = null;
        if(findePlazo !=null){
            val = findePlazo.getTime().toString();
        }else{
            val = "No definido";
        }
        return "Soy una CuentaPlazoFijo  " + super.toString() + "  Vence: "
                + val + ";  Intereses: " + intereses;
    }
}
