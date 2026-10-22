
public class Banco {
    private CuentaBancaria[] cuentas = new CuentaBancaria[20];
    private int cantidadCuentas = 0;

    public void agregarCuenta(CuentaBancaria c) {
        if (cantidadCuentas >= cuentas.length) {
            throw new IllegalStateException("La capacidad del banco ha alcanzado su maximo de cuentas.");
        }
        if (buscarPorNumero(c.getNumero()) != -1) {
            throw new IllegalArgumentException("Ya existe una cuenta con el número " + c.getNumero());
        }
        cuentas[cantidadCuentas++] = c;
    }

    public int buscarPorNumero(int numero) {
        try {
            return buscarRecursivo(numero, 0);
        } catch (IllegalArgumentException e) {
            return -1; 
        }
    }

    private int buscarRecursivo(int numero, int indice) {
        if (indice >= cantidadCuentas) {
            throw new IllegalArgumentException("Cuenta con número " + numero + " no encontrada.");
        }
        if (cuentas[indice].getNumero() == numero) {
            return indice;
        }
        return buscarRecursivo(numero, indice + 1);
    }

    public void ordenarPorSaldo() {
        ordenarPorSaldo(0);
    }

    private void ordenarPorSaldo(int i) {
        if (i >= cantidadCuentas - 1)
            return;

        int minID = i;
        for (int j = i + 1; j < cantidadCuentas; j++) {
            if (cuentas[j].getSaldo() < cuentas[minID].getSaldo()) {
                minID = j;
            }
        }

        CuentaBancaria temp = cuentas[i];
        cuentas[i] = cuentas[minID];
        cuentas[minID] = temp;

        ordenarPorSaldo(i + 1);
    }

    public void transferir(int numeroOrigen, int numeroDestino, double monto) {
        if (numeroOrigen == numeroDestino) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser la misma cuenta.");
        }

        CuentaBancaria origen = cuentas[buscarPorNumero(numeroOrigen)];
        CuentaBancaria destino = cuentas[buscarPorNumero(numeroDestino)];

        if (!origen.retirar(monto)) {
            throw new IllegalStateException("Fondos insuficientes en la cuenta de origen.");
        }

        try {
            destino.depositar(monto);
        } catch (Exception e) {
            origen.reintegrar(monto);
            throw e;
        }
    }

    public void aplicarInteresesGlobal() {
        for (int i = 0; i < cantidadCuentas; i++) {
            try {
                cuentas[i].registrarIntereses();
            } catch (Exception e) {
                System.out.println("Error aplicando intereses a cuenta #" + cuentas[i].getNumero());
            }
        }
    }

    public double saldoTotalDelBanco() {
        return sumarSaldos(0);
    }

    private double sumarSaldos(int indice) {
        if (indice >= cantidadCuentas)
            return 0;
        return cuentas[indice].getSaldo() + sumarSaldos(indice + 1);
    }

    public void generarReporte() {
        ordenarPorSaldo();
        System.out.println("\nReporte de Cuentas");
        for (int i = 0; i < cantidadCuentas; i++) {
            System.out.println(cuentas[i].toString());
        }
    }

    public CuentaBancaria getCuenta(int indice) {
        return cuentas[indice];
    }
}
