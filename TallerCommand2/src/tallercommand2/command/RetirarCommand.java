
package tallercommand2.command;

import tallercommand2.model.CuentaBancaria;

public class RetirarCommand implements Command {

    private final CuentaBancaria cuenta;
    private final double monto;
    private boolean ejecutado;

    public RetirarCommand(CuentaBancaria cuenta, double monto) {
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
                    "Este retiro ya fue ejecutado.");
        }

        cuenta.retirar(monto);
        ejecutado = true;

        System.out.println(
                "Retiro realizado: " + monto);
    }

    @Override
    public void deshacer() {
        if (!ejecutado) {
            throw new IllegalStateException(
                    "No se puede deshacer un retiro no ejecutado.");
        }

        cuenta.depositar(monto);
        ejecutado = false;

        System.out.println(
                "Retiro revertido: " + monto);
    }
}
