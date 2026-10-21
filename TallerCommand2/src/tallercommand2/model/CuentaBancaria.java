
package tallercommand2.model;

public class CuentaBancaria {

    private final String numeroCuenta;
    private final String titular;
    private double saldo;

    private static final Object BLOQUEO_TRANSFERENCIAS = new Object();

    public CuentaBancaria(
            String numeroCuenta,
            String titular,
            double saldoInicial) {

        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new IllegalArgumentException(
                    "El numero de cuenta es obligatorio.");
        }

        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException(
                    "El titular es obligatorio.");
        }

        validarMontoNoNegativo(saldoInicial);

        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public synchronized void depositar(double monto) {
        validarMontoPositivo(monto);

        saldo += monto;
    }

    public synchronized void retirar(double monto) {
        validarMontoPositivo(monto);

        if (saldo < monto) {
            throw new IllegalStateException(
                    "Saldo insuficiente en la cuenta "
                    + numeroCuenta);
        }

        saldo -= monto;
    }

    public void transferir(
            CuentaBancaria destino,
            double monto) {

        if (destino == null) {
            throw new IllegalArgumentException(
                    "La cuenta destino es obligatoria.");
        }

        if (destino == this) {
            throw new IllegalArgumentException(
                    "No se puede transferir a la misma cuenta.");
        }

        validarMontoPositivo(monto);

        synchronized (BLOQUEO_TRANSFERENCIAS) {
            synchronized (this) {
                synchronized (destino) {

                    if (saldo < monto) {
                        throw new IllegalStateException(
                                "Saldo insuficiente en la cuenta "
                                + numeroCuenta);
                    }

                    saldo -= monto;
                    destino.saldo += monto;
                }
            }
        }
    }

    public synchronized double consultarSaldo() {
        return saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    private static void validarMontoPositivo(double monto) {
        if (!Double.isFinite(monto) || monto <= 0) {
            throw new IllegalArgumentException(
                    "El monto debe ser un numero positivo y finito.");
        }
    }

    private static void validarMontoNoNegativo(double monto) {
        if (!Double.isFinite(monto) || monto < 0) {
            throw new IllegalArgumentException(
                    "El saldo inicial no puede ser negativo.");
        }
    }

    @Override
    public synchronized String toString() {
        return "Cuenta: " + numeroCuenta
                + " | Titular: " + titular
                + " | Saldo: " + saldo;
    }
}
