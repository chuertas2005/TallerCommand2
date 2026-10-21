
package tallercommand2;

import tallercommand2.client.Cliente;
import tallercommand2.command.Command;
import tallercommand2.command.ConsultarSaldoCommand;
import tallercommand2.invoker.CajeroAutomatico;
import tallercommand2.model.CuentaBancaria;

public class Main {

    public static void main(String[] args) {

        // crear las cuentas, invoker, cliente y asociarlo al invoker
        CuentaBancaria cuenta1 = new CuentaBancaria(
                "1001", "Ana", 500000);
        CuentaBancaria cuenta2 = new CuentaBancaria(
                "1002", "Carlos", 200000);

        CajeroAutomatico cajero = new CajeroAutomatico();

        Cliente cliente = new Cliente(cajero);

        System.out.println("=== SALDOS INICIALES ===");
        System.out.println(cuenta1);
        System.out.println(cuenta2);

        System.out.println("\n=== OPERACIONES ===");

        // pruebas de deposito, retiro, transferencia y consulta de saldo
        Command deposito =
                cliente.crearComandoDeposito(cuenta1, 100000);

        cliente.solicitarOperacion(deposito);

        Command retiro =
                cliente.crearComandoRetiro(cuenta1, 50000);

        cliente.solicitarOperacion(retiro);

        Command transferencia =
                cliente.crearComandoTransferencia(
                        cuenta1, cuenta2, 150000);

        cliente.solicitarOperacion(transferencia);

        ConsultarSaldoCommand consulta =
                (ConsultarSaldoCommand)
                cliente.crearComandoConsulta(cuenta1);

        cliente.solicitarOperacion(consulta);

        System.out.println(
                "\nSaldo obtenido por el comando: " + consulta.obtenerSaldoConsultado());

        System.out.println("\n=== SALDOS DESPUES DE OPERAR ===");
        System.out.println(cuenta1);
        System.out.println(cuenta2);

        // deshacer la ultima operacion registrada (la consulta no cambia saldo)
     
        System.out.println("\n=== DESHACER ULTIMO COMANDO ===");
        cajero.deshacerUltimo();

        // deshacer transferencia.
        cajero.deshacerUltimo();

        System.out.println("\n=== SALDOS DESPUES DE REVERTIR ===");
        System.out.println(cuenta1);
        System.out.println(cuenta2);

        // caso de prueba varias operaciones consecutivas
        System.out.println("\n=== EJECUCION EN COLA ===");

        cliente.programarOperacion(
                cliente.crearComandoDeposito(cuenta1, 25000));

        cliente.programarOperacion(
                cliente.crearComandoRetiro(cuenta2, 10000));

        cliente.programarOperacion(
                cliente.crearComandoConsulta(cuenta2));

        cajero.ejecutarTodos();

        System.out.println("\n=== SALDOS FINALES ===");
        System.out.println(cuenta1);
        System.out.println(cuenta2);

        System.out.println(
                "\nComandos pendientes: "
                + cajero.cantidadComandosPendientes());

        System.out.println(
                "Comandos en historial: "
                + cajero.cantidadComandosEjecutados());
    }
}
