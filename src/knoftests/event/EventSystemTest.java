package knoftests.event;


import knof.event.EventHandler;
import knof.event.EventSystem;
import knof.event.IEvent;
import knof.event.events.ChallengeEvent;
import knof.event.events.ListEvent;
import knof.event.events.StatusEvent;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventSystemTest {
    @Test
    public void testSystem() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("SVR GAME CHALLENGE {CHALLENGER: \"john\", CHALLENGENUMBER: \"4\", GAMETYPE: \"Tic-tac-toe\", TURNTIME: \"100\"}");
        System.out.println(ev);
    }

    @Test
    public void testOk() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("OK");
        assertEquals(ev , StatusEvent.Ok.class);
    }

    @Test
    public void testGamelist() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("GAMELIST");
        assertEquals(ev, ListEvent.Games.class);
    }

    @Test
    public void testPlayerlist() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("PLAYERLIST");
        assertEquals(ev, ListEvent.Players.class);
    }

//    @Test
//    public void testPlayerlist() {
//        EventSystem es = new EventSystem();
//        IEvent ev = es.parse("GAME MATCH");
//        assertEquals(ev, ListEvent.Players.class);
//    }

    class SystemTester {
        public String challenger = "NOBODY";

        @EventHandler
        public void onChallenge(ChallengeEvent ev) {
            this.challenger = ev.challenger;
        }
    }

    @Test
    public void testRegister() {
        EventSystem es = new EventSystem();
        SystemTester st = new SystemTester();
        ChallengeEvent ev = new ChallengeEvent();

        ev.challenger = "SOMEBODY";

        es.register(st);
        es.emitEvent(ev);

        assertEquals(ev.challenger, st.challenger);
    }
}
