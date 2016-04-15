package knoftests.event;


import knof.event.EventSystem;
import knof.event.IEvent;
import org.junit.Test;

public class EventSystemTest {
    @Test
    public void testSystem() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("SVR GAME CHALLENGE {CHALLENGER: \"john\", CHALLENGENUMBER: \"4\", GAMETYPE: \"Tic-tac-toe\", TURNTIME: \"100\"}");
        System.out.println(ev);
    }
}
