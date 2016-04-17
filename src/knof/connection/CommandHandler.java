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
    private final BlockingQueue<Boolean> discardQueue = new LinkedBlockingQueue<>();

    public CommandHandler(EventSystem eventSystem, PrintWriter out) {
        this.out = out;
        eventSystem.register(this);
    }

    public StatusEvent sendCommand(Command command, boolean blocking, Object... arguments) {
        this.out.println(command.format(arguments));
        try {
            if (blocking) {
                    discardQueue.put(false);
                    StatusEvent ev = eventQueue.take();
                    return ev;
            } else {
                discardQueue.put(true);
                return null;
            }
        } catch (InterruptedException e) {
            return new StatusEvent.Error("CLIENT: " + e.getMessage());
        }
    }

    public void discard(int n) {
        for (int i=0; i<n; i++) {
            try {
                discardQueue.put(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onStatus(StatusEvent ev) {
        try {
            if (!discardQueue.take()) {
                eventQueue.put(ev);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
