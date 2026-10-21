
package tallercommand2.command;

import tallercommand2.model.CuentaBancaria;

public class TransferirCommand implements Command {

    private final CuentaBancaria origen;
    private final CuentaBancaria destino;
    private final double monto;
    private boolean ejecutado;

    public TransferirCommand(
            CuentaBancaria origen,
            CuentaBancaria destino,
            double monto) {

        if (origen == null || destino == null) {
            throw new IllegalArgumentException(
                    "Las cuentas de origen y destino son obligatorias.");
        }

        this.origen = origen;
        this.destino = destino;
        this.monto = monto;
        this.ejecutado = false;
    }

    @Override
    public void ejecutar() {
        if (ejecutado) {
            throw new IllegalStateException(
                    "Esta transferencia ya fue ejecutada.");
        }

        origen.transferir(destino, monto);
        ejecutado = true;

        System.out.println(
                "Transferencia realizada: " + monto
                + " desde " + origen.getNumeroCuenta()
                + " hacia " + destino.getNumeroCuenta());
    }

    @Override
    public void deshacer() {
        if (!ejecutado) {
            throw new IllegalStateException(
                    "No se puede deshacer una transferencia no ejecutada.");
        }

        destino.transferir(origen, monto);
        ejecutado = false;

        System.out.println(
                "Transferencia revertida: "
                + monto);
    }
}
