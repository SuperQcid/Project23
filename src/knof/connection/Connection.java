package knof.connection;

import knof.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
    private final PrintWriter out;
    private final BufferedReader in;
    private boolean running = true;

    public Connection(String host, int port, String username) throws IOException {
        Socket socket = new Socket(host, port);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));



        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public synchronized void sendCommand(Command command, Object arguments) {
        this.sendMessage(command.format(arguments));
    }

    private synchronized void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        while (running) {
            try {
                String message = this.in.readLine();
                //TODO: Pass messages to messagehandler
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
