
package tallercommand2.invoker;

import tallercommand2.command.Command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CajeroAutomatico {

    private final Deque<Command> historial;
    private final Deque<Command> comandosPendientes;

    public CajeroAutomatico() {
        historial = new ArrayDeque<>();
        comandosPendientes = new ArrayDeque<>();
    }

    public void agregarComando(Command comando) {
        if (comando == null) {
            throw new IllegalArgumentException(
                    "El comando no puede ser nulo.");
        }

        comandosPendientes.addLast(comando);
    }

    public void ejecutarComando(Command comando) {
        if (comando == null) {
            throw new IllegalArgumentException(
                    "El comando no puede ser nulo.");
        }

        comando.ejecutar();
        historial.addLast(comando);
    }

    public void ejecutarTodos() {
        while (!comandosPendientes.isEmpty()) {
            Command comando = comandosPendientes.peekFirst();

            /*
             * Si falla la ejecucion, el comando permanece pendiente.
             * Si tiene exito, se registra en el historial.
             */
            ejecutarComando(comando);
            comandosPendientes.removeFirst();
        }
    }

    public void deshacerUltimo() {
        if (historial.isEmpty()) {
            System.out.println(
                    "No hay comandos ejecutados para deshacer.");
            return;
        }

        Command ultimo = historial.peekLast();

        /*
         * Se elimina del historial solo si deshacer() termina
         * correctamente.
         */
        ultimo.deshacer();
        historial.removeLast();
    }

    public int cantidadComandosPendientes() {
        return comandosPendientes.size();
    }

    public int cantidadComandosEjecutados() {
        return historial.size();
    }
}
