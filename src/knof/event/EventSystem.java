package knof.event;

import knof.event.events.ChallengeEvent;
import knof.event.events.ErrorEvent;
import knof.event.events.ListEvent;
import knof.event.events.OkEvent;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventSystem {
    private List<EventEntry> eventRegister = new LinkedList<>();
    private WeakHashMap<IEventHandler,Void> eventHandlers = new WeakHashMap<>();

    public EventSystem() {
        this.add("OK", OkEvent.class);
        this.add("SVR GAMELIST (.*)", ListEvent.Games.class);
        this.add("SVR PLAYERLIST (.*)", ListEvent.Players.class);
        this.add("SVR GAME CHALLENGE CANCELLED (.*)", ChallengeEvent.Cancel.class);
        this.add("SVR GAME CHALLENGE (.*)", ChallengeEvent.class);
    }

    public Event parse(String message) {
        if(message.startsWith("ERR ")) {
            return new ErrorEvent(message.substring(4));
        }
        Iterator<EventEntry> it = eventRegister.iterator();
        while (it.hasNext()) {
            EventEntry ee = it.next();
            Matcher matcher = ee.match(message);
            if(matcher!=null) {
                return this.createEvent(ee.eventClass, matcher.group(1));
            }
        }
        return null;
    }

    private Event createEvent(Class<? extends Event> eventClass, String data) {
        // TODO: Parse json data
        try {
            Event event = eventClass.newInstance();
            for(Field field: event.getClass().getFields()) {
                JsonField jsonField = field.getAnnotation(JsonField.class);
                if (jsonField!=null) {
                    if(jsonField.full()) {
                        // TODO: Set field to parsed data
                    }
                    else {
                        String key = field.getName().toUpperCase();
                        // TODO: Set local variable to data with key
                        if(jsonField.reparse()) {
                            // TODO: Parse data again
                        }
                        // TODO: Set field to data
                    }
                }
            }
            return event;
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO: Give warning
            return null;
        }
    }

    private void add(String regex, Class<? extends Event> eventClass) {
        this.eventRegister.add(new EventEntry(eventClass, regex));
    }

    private class EventEntry {
        public final Class<? extends Event> eventClass;
        public final Pattern regex;

        public EventEntry(Class<? extends Event> eventClass, String pattern) {
            this.eventClass = eventClass;
            this.regex = Pattern.compile(pattern);
        }

        public Matcher match(String message) {
            Matcher matcher = this.regex.matcher(message);
            if(matcher.matches()) {
                return matcher;
            }
            return null;
        }

    }

    public void register(IEventHandler eventHandler) {
        eventHandlers.put(eventHandler, null);
    }

    @FunctionalInterface
    interface IEventHandler<E extends Event> {
        void handle(E event);
    }
}
