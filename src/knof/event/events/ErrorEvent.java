package knof.event.events;

import knof.event.Event;

public class ErrorEvent extends Event {
    public String reason;

    public ErrorEvent(String reason) {
        this.reason = reason;
    }
}
