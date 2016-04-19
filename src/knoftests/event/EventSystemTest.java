package knoftests.event;


import knof.event.EventHandler;
import knof.event.EventSystem;
import knof.event.IEvent;
import knof.event.events.ChallengeEvent;
import knof.event.events.MessageEvent;
import knof.event.events.MoveEvent;
import knof.event.events.ListEvent;
import knof.event.events.StatusEvent;
import org.junit.Test;
import static org.junit.Assert.*;

public class EventSystemTest {
    @Test
    public void testChallenge() {
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME CHALLENGE {CHALLENGER: \"john\", CHALLENGENUMBER: \"4\", GAMETYPE: \"Tic-tac-toe\", TURNTIME: \"100\"}");
        assertTrue(iev instanceof ChallengeEvent);
        ChallengeEvent cev = (ChallengeEvent) iev;
        assertEquals(cev.challenger, "john");
        assertEquals(cev.id, 4);
        assertEquals(cev.gameType, "Tic-tac-toe");
        assertEquals(cev.turnTime, 100);
    }

    @Test
    public void testMove(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME MOVE {PLAYER: \"TestOpponent\", DETAILS: \"TestDetails\", MOVE: \"55\"}");
        assertTrue(iev instanceof MessageEvent);
        MoveEvent mev = (MoveEvent) iev;
        assertEquals(mev.player, "TestOpponent");
        assertEquals(mev.details, "TestDetails");
        assertEquals(mev.move, 55);
    }

    @Test
    public void testMessage(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR MESSAGE {PLAYERNAME: \"TestOpponent\", MESSAGE: \"Test Message TestMessage\"}");
        assertTrue(iev instanceof MessageEvent);
        MessageEvent mev = (MessageEvent) iev;
        assertEquals(mev.playerName, "TestOpponent");
        assertEquals(mev.message, "Test Message TestMessage");
    }


    /*
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
    }*/
}
