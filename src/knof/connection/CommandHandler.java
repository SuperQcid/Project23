package knof.connection;

import knof.command.Command;
import knof.event.EventHandler;
import knof.event.EventSystem;
import knof.event.events.StatusEvent;

import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandHandler {
    private final BlockingQueue<StatusEvent> eventQueue = new LinkedBlockingQueue<>();
    private final PrintWriter out;
    private int discard;

    public CommandHandler(EventSystem eventSystem, PrintWriter out) {
        this.out = out;
        eventSystem.register(this);
    }

    public StatusEvent sendCommand(Command command, boolean blocking, Object... arguments) {
        this.out.println(command.format(arguments));
        if(blocking) {
            try {
                StatusEvent ev = eventQueue.take();
                return ev;
            } catch (InterruptedException e) {
                return new StatusEvent.Error("CLIENT: " + e.getMessage());
            }
        }
        else {
            synchronized (this) {
                discard++;
            }
            return null;
        }
    }

    public void discard(int n) {
        synchronized (this) {
            discard += n;
        }
    }

    @EventHandler
    public void onStatus(StatusEvent ev) {
        synchronized (this) {
            if (discard > 0) {
                discard--;
            } else {
                try {
                    eventQueue.put(ev);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
