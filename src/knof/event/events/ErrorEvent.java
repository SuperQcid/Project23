package knof.event.events;

import knof.event.IEvent;

public class ErrorEvent implements IEvent {
    public String reason;

    public ErrorEvent(String reason) {
        this.reason = reason;
    }
}
