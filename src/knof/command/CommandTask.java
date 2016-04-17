package knof.command;

import knof.connection.Connection;
import knof.model.Server;

import java.lang.ref.WeakReference;
import java.util.TimerTask;

public class CommandTask extends TimerTask {
    private final Connection connection;
    private final Command command;
    private final Object[] arguments;
    private WeakReference<Object> subject;

    public CommandTask(Connection connection, Object subject, Command command, Object... arguments) {
        this.connection = connection;
        this.command = command;
        this.arguments = arguments;
        this.subject = new WeakReference<Object>(subject);
    }


    @Override
    public void run() {
        connection.sendCommand(command, arguments);
        System.out.println(subject.get());
    }
}
