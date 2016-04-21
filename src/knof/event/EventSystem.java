package knof.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import knof.event.events.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EventSystem {
    private List<EventEntry> eventRegister = new LinkedList<>();
    private WeakHashMap<Object, Map<Method,Class<? extends IEvent>>> eventReceivers = new WeakHashMap<>();
    private final ObjectMapper mapper;

    public EventSystem() {
        mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        this.add("OK", StatusEvent.Ok.class);
        this.add("SVR GAMELIST (.*)", ListEvent.Games.class);
        this.add("SVR PLAYERLIST (.*)", ListEvent.Players.class);
        this.add("SVR GAME CHALLENGE CANCELLED (.*)", ChallengeEvent.Cancel.class);
        this.add("SVR GAME CHALLENGE (.*)", ChallengeEvent.class);
        this.add("SVR GAME MOVE (.*)", MoveEvent.class);
        this.add("SVR GAME MATCH (.*)", MatchEvent.class);
        this.add("SVR GAME YOURTURN (.*)", TurnEvent.class);
        this.add("SVR GAME WIN (.*)", GameResultEvent.Win.class);
        this.add("SVR GAME LOSS (.*)", GameResultEvent.Loss.class);
        this.add("SVR GAME DRAW (.*)", GameResultEvent.Draw.class);
        this.add("SVR GAME (.*)", GameResultEvent.Forfeit.class);
        this.add("SVR MESSAGE (.*)", MessageEvent.class);
    }

    public void handleMessage(String message) {
        IEvent event = this.parse(message);
        if(event!=null) {
            emitEvent(event);
        }
        else {
            System.err.println("WARNING: Message not handled: "+message);
        }
    }

    /**
     * Parse message to event
     * @param message message to parse
     * @return event
     */
    public IEvent parse(String message) {
        if(message.startsWith("ERR ")) {
            return new StatusEvent.Error(message.substring(4));
        }
        if(message.equals("OK")) {
            return new StatusEvent.Ok();
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

    /**
     * Create event from json data
     * @param eventClass class to instantiate
     * @param data json data
     * @return event
     * @throws IOException
     */
    private IEvent createEvent(Class<? extends IEvent> eventClass, String data) throws IOException {
        return mapper.readValue(data, eventClass);
    }

    /**
     * Add pattern for event class
     * @param regex command pattern with capture group for json string
     * @param eventClass event class
     */
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

    /**
     * Emit event to all registered objects with valid methods
     * @param event event to emit
     */
    public void emitEvent(IEvent event) {
        try {
            for (Map.Entry<Object, Map<Method, Class<? extends IEvent>>> receiver : eventReceivers.entrySet()) {
                Map<Method, Class<? extends IEvent>> receiverMethods = receiver.getValue();
                for (Map.Entry<Method, Class<? extends IEvent>> entry : receiverMethods.entrySet()) {
                    if (entry.getValue().isInstance(event)) {
                        Method method = entry.getKey();
                        Object object = receiver.getKey();
                        EventHandler eventHandler = method.getAnnotation(EventHandler.class);
                        if (eventHandler.later()) {
                            Platform.runLater(() -> {
                                try {
                                    method.invoke(object, event);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        else {
                            method.invoke(object, event);
                        }
                    }
                }
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register object to receive events on methods marked with @EventHandler
     * @param receiver object to receive events
     */
    public void register(Object receiver) {
        HashMap<Method,Class<? extends IEvent>> methodMap = new HashMap<>();

        Class receiverClass = receiver.getClass();
        Method[] classMethods = receiverClass.getDeclaredMethods();
        for(Method method: classMethods) {
            if(method.isAnnotationPresent(EventHandler.class)) {
                if (method.getParameterCount() == 1 && IEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
                    method.setAccessible(true);
                    methodMap.put(method, (Class<? extends IEvent>) method.getParameterTypes()[0]);
                }
                else {
                    System.err.println("WARNING: Method "+method.getName()+" on "+receiverClass.getName()+" is not valid as eventhandler.");
                }
            }
        }

        if (!methodMap.isEmpty()) {
            eventReceivers.put(receiver, methodMap);
        }
    }
}
