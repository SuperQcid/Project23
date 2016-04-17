package knof.event.events;

import knof.event.IEvent;

public class StatusEvent implements IEvent {
    public static class Ok extends StatusEvent {}

    public static class Error extends StatusEvent {
        public String reason;

        public Error(String reason) {
            this.reason = reason;
        }
    }
}
