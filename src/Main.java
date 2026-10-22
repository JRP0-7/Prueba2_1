
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();
        int opcion = 0;

        do {
            System.out.println("\n-Menu");
            System.out.println("1. Crear Cuenta");
            System.out.println("2. Depositar");
            System.out.println("3. Retirar");
            System.out.println("4. Transferir");
            System.out.println("5. Aplicar Intereses Globales");
            System.out.println("6. Reporte y Saldo Total");
            System.out.println("7. Ver Historial");
            System.out.println("8. Girar Cheque");
            System.out.println("9. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("Tipo: 1. Ahorro | 2. Cheques | 3. Plazo Fijo");
                        int tipo = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Número de cuenta: ");
                        int num = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Cliente: ");
                        String cliente = sc.nextLine();
                        System.out.print("Moneda: ");
                        String moneda = sc.nextLine();

                        if (tipo == 1) {
                            banco.agregarCuenta(new CuentaAhorro(num, cliente, moneda));
                        } else if (tipo == 2) {
                            System.out.print("Límite sobregiro: ");
                            double limite = sc.nextDouble();
                            sc.nextLine();
                            banco.agregarCuenta(new CuentaCheques(num, cliente, moneda, limite));
                        } else if (tipo == 3) {
                            CuentaPlazoFijo cp = new CuentaPlazoFijo(num, cliente, moneda);
                            System.out.print("Año vencimiento: ");
                            int a = sc.nextInt();
                            System.out.print("Mes (1-12): ");
                            int m = sc.nextInt();
                            System.out.print("Día: ");
                            int d = sc.nextInt();
                            sc.nextLine();
                            cp.setPlazo(a, m, d);
                            banco.agregarCuenta(cp);
                        }
                        System.out.println("Cuenta creada con éxito.");
                        break;

                    case 2:
                        System.out.print("Número de cuenta: ");
                        int idDep = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Monto: ");
                        double mDepo = sc.nextDouble();
                        sc.nextLine();
                        banco.getCuenta(banco.buscarPorNumero(idDep)).depositar(mDepo);
                        System.out.println("Depósito realizado.");
                        break;

                    case 3:
                        System.out.print("Número de cuenta: ");
                        int idRet = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Monto: ");
                        double mRet = sc.nextDouble();
                        sc.nextLine();
                        if (banco.getCuenta(banco.buscarPorNumero(idRet)).retirar(mRet)) {
                            System.out.println("Retiro exitoso.");
                        } else {
                            System.out.println("Fondos insuficientes.");
                        }
                        break;

                    case 4:
                        System.out.print("Cuenta Origen: ");
                        int cOrigen = sc.nextInt();
                        System.out.print("Cuenta Destino: ");
                        int cDestino = sc.nextInt();
                        System.out.print("Monto: ");
                        double mTra = sc.nextDouble();
                        sc.nextLine();
                        banco.transferir(cOrigen, cDestino, mTra);
                        System.out.println("Transferencia exitosa.");
                        break;

                    case 5:
                        banco.aplicarInteresesGlobal();
                        System.out.println("Intereses aplicados.");
                        break;

                    case 6:
                        banco.generarReporte();
                        System.out.printf("Saldo Total del Banco: %.2f%n", banco.saldoTotalDelBanco());
                        break;

                    case 7:
                        System.out.print("Número de cuenta: ");
                        int nCuenta = sc.nextInt();
                        sc.nextLine();
                        banco.getCuenta(banco.buscarPorNumero(nCuenta)).imprimirHistorial();
                        break;

                    case 8:
                        System.out.print("Número de cuenta: ");
                        int nAC = sc.nextInt();
                        sc.nextLine();
                        CuentaBancaria c = banco.getCuenta(banco.buscarPorNumero(nAC));
                        if (c instanceof CuentaCheques) {
                            System.out.print("Monto del cheque: ");
                            double mCheque = sc.nextDouble();
                            sc.nextLine();
                            ((CuentaCheques) c).girarCheque(mCheque);
                            System.out.println("Cheque girado con éxito.");
                        } else {
                            System.out.println("La cuenta no es de tipo Cheques.");
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 9);
        System.out.println("Saliendo");
    }
}
