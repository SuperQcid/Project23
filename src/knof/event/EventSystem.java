package knof.event;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import knof.event.events.ChallengeEvent;
import knof.event.events.ErrorEvent;
import knof.event.events.ListEvent;
import knof.event.events.OkEvent;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventSystem {
    private List<EventEntry> eventRegister = new LinkedList<>();
    private WeakHashMap<Object, Void> eventReceivers = new WeakHashMap<>();
    private final ObjectMapper mapper;

    public EventSystem() {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        this.add("OK", OkEvent.class);
        this.add("SVR GAMELIST (.*)", ListEvent.Games.class);
        this.add("SVR PLAYERLIST (.*)", ListEvent.Players.class);
        this.add("SVR GAME CHALLENGE CANCELLED (.*)", ChallengeEvent.Cancel.class);
        this.add("SVR GAME CHALLENGE (.*)", ChallengeEvent.class);
    }

    public IEvent parse(String message) {
        if(message.startsWith("ERR ")) {
            return new ErrorEvent(message.substring(4));
        }
        Iterator<EventEntry> it = eventRegister.iterator();
        while (it.hasNext()) {
            EventEntry ee = it.next();
            Matcher matcher = ee.match(message);
            if(matcher!=null) {
                try {
                    return this.createEvent(ee.eventClass, matcher.group(1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private IEvent createEvent(Class<? extends IEvent> eventClass, String group) throws IOException {
        return mapper.readValue(group, eventClass);
    }

    private void add(String regex, Class<? extends IEvent> eventClass) {
        this.eventRegister.add(new EventEntry(eventClass, regex));
    }

    private class EventEntry {
        public final Class<? extends IEvent> eventClass;
        public final Pattern regex;

        public EventEntry(Class<? extends IEvent> eventClass, String pattern) {
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

    public void register(Object receiver) {
        eventReceivers.put(receiver, null);
    }
}
