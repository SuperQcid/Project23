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
import java.net.SocketException;

public class Connection implements Runnable {
    private final PrintWriter out;
    private final BufferedReader in;
    private boolean running = true;
    public final EventSystem eventSystem;
    private CommandHandler commandHandler;
    private String playerName;

    public Connection(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Create EventSystem
        this.eventSystem = new EventSystem();
        this.commandHandler = new CommandHandler(this.eventSystem, this.out);

        // Create new thread and start it
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized void sendCommand(Command command, Object... arguments) {
        this.commandHandler.sendCommand(command, null, arguments);
    }

    public synchronized void sendCommandWithCallBack(CommandHandler.Callback callback, Command command, Object... arguments) {
        this.commandHandler.sendCommand(command, callback, arguments);
    }
    
    public void setPlayerName(String playerName){
    	this.playerName = playerName;
    }
    
    public String getPlayerName(){
    	return this.playerName;
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
