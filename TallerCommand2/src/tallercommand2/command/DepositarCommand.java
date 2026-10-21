
package tallercommand2.command;


import tallercommand2.model.CuentaBancaria;

public class DepositarCommand implements Command {

    private final CuentaBancaria cuenta;
    private final double monto;
    private boolean ejecutado;

    public DepositarCommand(CuentaBancaria cuenta, double monto) {
        if (cuenta == null) {
            throw new IllegalArgumentException(
                    "La cuenta es obligatoria.");
        }

        this.cuenta = cuenta;
        this.monto = monto;
        this.ejecutado = false;
    }

    @Override
    public void ejecutar() {
        if (ejecutado) {
            throw new IllegalStateException(
                    "Este deposito ya fue ejecutado.");
        }

        cuenta.depositar(monto);
        ejecutado = true;

        System.out.println(
                "Deposito realizado: " + monto);
    }

    @Override
    public void deshacer() {
        if (!ejecutado) {
            throw new IllegalStateException(
                    "No se puede deshacer un deposito no ejecutado.");
        }

        cuenta.retirar(monto);
        ejecutado = false;

        System.out.println(
                "Deposito revertido: " + monto);
    }
}
