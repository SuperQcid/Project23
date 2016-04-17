package knof.connection;

import knof.command.Command;
import knof.event.EventHandler;
import knof.event.EventSystem;
import knof.event.events.StatusEvent;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandHandler {
    private final BlockingQueue<StatusEvent> eventQueue = new LinkedBlockingQueue<>();
    private final PrintWriter out;
    private int discard;

    public CommandHandler(EventSystem eventSystem, PrintWriter out) {
        this.out = out;
        eventSystem.register(this);
    }

    public synchronized StatusEvent sendCommand(Command command, boolean blocking, Object... arguments) {
        this.out.println(command.format(arguments));
        if(blocking) {
            try {
                return eventQueue.take();
            } catch (InterruptedException e) {
                return new StatusEvent.Error("CLIENT: " + e.getMessage());
            }
        }
        else {
            discard++;
            return null;
        }
    }

    public void discard(int n) {
        discard+=n;
    }

    @EventHandler
    public void onStatus(StatusEvent ev) {
        synchronized (this) {
            if (discard > 0) {
                discard--;
            }
            else {
                try {
                    eventQueue.put(ev);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
