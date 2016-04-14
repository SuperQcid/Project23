package knof.event.events;

import knof.event.Event;
import knof.event.JsonField;

public class ListEvent extends Event {
    @JsonField(full = true)
    public String[] list;

    public class Players extends ListEvent {};
    public class Games extends ListEvent {};
}
