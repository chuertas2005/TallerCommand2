
package tallercommand2.command;

import tallercommand2.model.CuentaBancaria;

public class ConsultarSaldoCommand implements Command {

    private final CuentaBancaria cuenta;
    private double saldoConsultado;

    public ConsultarSaldoCommand(CuentaBancaria cuenta) {
        if (cuenta == null) {
            throw new IllegalArgumentException(
                    "La cuenta es obligatoria.");
        }

        this.cuenta = cuenta;
    }

    @Override
    public void ejecutar() {
        saldoConsultado = cuenta.consultarSaldo();

        System.out.println(
                "Saldo de la cuenta "
                + cuenta.getNumeroCuenta()
                + ": " + saldoConsultado);
    }

    @Override
    public void deshacer() {
        /*
         * Una consulta no modifica el estado de la cuenta.
         * Por lo tanto, no requiere una operacion inversa.
         */
    }

    public double obtenerSaldoConsultado() {
        return saldoConsultado;
    }
}
