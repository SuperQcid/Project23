package knof.connection;

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
        Socket socket = new Socket();
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //TODO: Create messagehandeler
        //TODO: Login using messagehandeler

        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }

    public synchronized boolean sendMessage(String message) {
        out.println(message);
        //TODO: Address messagesender and return command success
        return true;
    }

    @Override
    public void run() {
        while (running) {
            try {
                String message = this.in.readLine();
                //TODO: Pass messages to messagehandler
                System.out.println();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
