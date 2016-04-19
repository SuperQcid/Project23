package knof.event.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import knof.event.IEvent;

/**
 * Created by Henk Dieter Oordt on 19-4-2016.
 */
public class HelpEvent implements IEvent {
        public String helpInformation;

        public HelpEvent(String helpInformation){
            this.helpInformation = helpInformation;
        }
}
