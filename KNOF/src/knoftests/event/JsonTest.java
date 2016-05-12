package knoftests.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import knof.event.events.ChallengeEvent;
import knof.event.events.ListEvent;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class JsonTest {
    @Test
    public void testChallengeEvent() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        String json = "{CHALLENGER: \"john\", CHALLENGENUMBER: \"4\", GAMETYPE: \"Tic-tac-toe\", TURNTIME: \"100\"}";
        ChallengeEvent ce = mapper.reader(ChallengeEvent.class).readValue(json);
        assertEquals(ce.challenger, "john");
        assertEquals(ce.id, 4);
        assertEquals(ce.gameType, "Tic-tac-toe");
        assertEquals(ce.turnTime, 100);
    }

    @Test
    public void testListEvent() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        String json = "[\"test\", \"test2\", \"john\", \"rose\", \"dave\", \"jade\"]";
        ListEvent.Players l = mapper.reader(ListEvent.Players.class).readValue(json);
        System.out.println(l);
    }
}
