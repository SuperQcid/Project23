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
    private final BlockingQueue<Callback> callbacks = new LinkedBlockingQueue<>();

    public CommandHandler(EventSystem eventSystem, PrintWriter out) {
        this.out = out;
        eventSystem.register(this);
    }

    public void sendCommand(Command command, Callback callback, Object... arguments) {
        this.out.println(command.format(arguments));
        try {
            if (callback == null) {
                callbacks.put(this::defaultCallback);
            } else {
                callbacks.put(callback);
            }
            out.println(command.format(arguments));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void discard(int n) {
        for (int i=0; i<n; i++) {
            try {
                callbacks.put(this::defaultCallback);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onStatus(StatusEvent ev) {
        try {
            callbacks.take().run(ev);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface Callback {
        void run(StatusEvent statusEvent);
    }

    public void defaultCallback(StatusEvent ev) {
        if(ev instanceof StatusEvent.Error) {
            System.err.println("ERROR: "+((StatusEvent.Error) ev).reason);
        }
    }
}
