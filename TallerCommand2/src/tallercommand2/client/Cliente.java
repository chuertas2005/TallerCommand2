
package tallercommand2.client;

import tallercommand2.command.Command;
import tallercommand2.command.DepositarCommand;
import tallercommand2.command.RetirarCommand;
import tallercommand2.command.TransferirCommand;
import tallercommand2.command.ConsultarSaldoCommand;
import tallercommand2.model.CuentaBancaria;
import tallercommand2.invoker.CajeroAutomatico;

public class Cliente {

    private final CajeroAutomatico cajero;

    public Cliente(CajeroAutomatico cajero) {
        if (cajero == null) {
            throw new IllegalArgumentException(
                    "El cajero es obligatorio.");
        }

        this.cajero = cajero;
    }

    public Command crearComandoDeposito(
            CuentaBancaria cuenta, double monto) {

        return new DepositarCommand(cuenta, monto);
    }

    public Command crearComandoRetiro(
            CuentaBancaria cuenta, double monto) {

        return new RetirarCommand(cuenta, monto);
    }

    public Command crearComandoTransferencia(
            CuentaBancaria origen,
            CuentaBancaria destino,
            double monto) {

        return new TransferirCommand(origen, destino, monto);
    }

    public Command crearComandoConsulta(CuentaBancaria cuenta) {
        return new ConsultarSaldoCommand(cuenta);
    }

    public void solicitarOperacion(Command comando) {
        cajero.ejecutarComando(comando);
    }

    public void programarOperacion(Command comando) {
        cajero.agregarComando(comando);
    }
}
