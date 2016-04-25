package knof.connection;

import javafx.application.Platform;
import knof.command.Command;
import knof.event.EventSystem;
import knof.event.events.StatusEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
    private final PrintWriter out;
    private final BufferedReader in;
    private boolean running = true;
    public final EventSystem eventSystem;
    private CommandHandler commandHandler;

    public Connection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.eventSystem = new EventSystem();
        this.commandHandler = new CommandHandler(this.eventSystem, this.out);

        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public synchronized void sendCommand(Command command, Object... arguments) {
        this.commandHandler.sendCommand(command, null, arguments);
    }

    public synchronized void sendCommandWithCallBack(CommandHandler.Callback callback, Command command, Object... arguments) {
        this.commandHandler.sendCommand(command, callback, arguments);
    }

    public void sendCommandWithCallBackLater(CommandHandler.Callback callback, Command command, Object... arguments) {
        this.commandHandler.sendCommand(
                command,
                (StatusEvent ev) -> Platform.runLater(()->callback.run(ev)),
                arguments);
    }

    public synchronized void sendMessage(String message) {
        out.println(message);
        this.commandHandler.discard(1);
    }

    @Override
    public void run() {
        while (running) {
            try {
                String message = this.in.readLine();
                this.eventSystem.handleMessage(message);
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
