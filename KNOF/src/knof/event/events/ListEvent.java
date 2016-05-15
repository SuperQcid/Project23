package knof.event.events;

import knof.event.IEvent;

import java.util.ArrayList;

public class ListEvent extends ArrayList<String> implements IEvent {

    public static class Players extends ListEvent {}
    public static class Games extends ListEvent {}
}
