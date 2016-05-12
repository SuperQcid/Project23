package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

import java.util.ArrayList;
import java.util.List;

public class ListEvent extends ArrayList<String> implements IEvent {

    public static class Players extends ListEvent {}
    public static class Games extends ListEvent {}
}
