package knoftests.event;


import knof.event.EventHandler;
import knof.event.EventSystem;
import knof.event.IEvent;
import knof.event.events.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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
        assertTrue(iev instanceof MoveEvent);
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

    @Test
    public void testOk() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("OK");
        assertTrue(ev instanceof StatusEvent.Ok);
    }
    @Test
    public void testForfeit(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME {PLAYERONESCORE: \"100\", PLAYERTWOSCORE: \"50\", COMMENT: \"Player forfeited match\"}");
        assertTrue(iev instanceof GameResultEvent.Forfeit);
        GameResultEvent.Forfeit fev = (GameResultEvent.Forfeit) iev;
        assertEquals(fev.playerOneScore, 100);
        assertEquals(fev.playerTwoScore, 50);
        assertEquals(fev.comment, "Player forfeited match");
    }

    @Test
    public void testError() {
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("ERR Fout");
        assertTrue(iev instanceof StatusEvent.Error);
        StatusEvent.Error err = (StatusEvent.Error) iev;
        assertEquals(err.reason, "Fout");
    }

    @Test
    public void testGamelist() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("SVR GAMELIST [\"game1\"]");
        assertTrue(ev instanceof ListEvent.Games);
        ListEvent.Games gamelist = (ListEvent.Games) ev;
        List<String> checklist = Arrays.asList("game1");
        assertThat(checklist, is(gamelist));
    }

    @Test
    public void testPlayerlist() {
        EventSystem es = new EventSystem();
        IEvent ev = es.parse("SVR PLAYERLIST [\"player1\"]");
        assertTrue(ev instanceof ListEvent.Players);
        ListEvent.Players playerlist = (ListEvent.Players) ev;
        List<String> checklist = Arrays.asList("player1");
        assertThat(checklist, is(playerlist));
    }

    @Test
    public void testDraw(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME DRAW {PLAYERONESCORE: \"345\", PLAYERTWOSCORE: \"312\", COMMENT: \"It's a draw!\"}");
        assertTrue(iev instanceof GameResultEvent.Draw);
        GameResultEvent.Draw dev = (GameResultEvent.Draw) iev;
        assertEquals(dev.playerOneScore, 345);
        assertEquals(dev.playerTwoScore, 312);
        assertEquals(dev.comment, "It's a draw!");
    }

    @Test
    public void testWin(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME WIN {PLAYERONESCORE: \"75\", PLAYERTWOSCORE: \"20\", COMMENT: \"You win!\"}");
        assertTrue(iev instanceof GameResultEvent.Win);
        GameResultEvent.Win dev = (GameResultEvent.Win) iev;
        assertEquals(dev.playerOneScore, 75);
        assertEquals(dev.playerTwoScore, 20);
        assertEquals(dev.comment, "You win!");
    }

    @Test
    public void testLoss(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME LOSS {PLAYERONESCORE: \"20\", PLAYERTWOSCORE: \"75\", COMMENT: \"You lose!\"}");
        assertTrue(iev instanceof GameResultEvent.Loss);
        GameResultEvent.Loss dev = (GameResultEvent.Loss) iev;
        assertEquals(dev.playerOneScore, 20);
        assertEquals(dev.playerTwoScore, 75);
        assertEquals(dev.comment, "You lose!");
    }

    @Test
    public void testYourTurn(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME YOURTURN {TURNMESSAGE: \"It's your turn!\"}");
        assertTrue(iev instanceof TurnEvent);
        TurnEvent ytev = (TurnEvent) iev;
        assertEquals(ytev.turnMessage, "It's your turn!");
    }

    @Test
    public void testMatch(){
        EventSystem es = new EventSystem();
        IEvent iev = es.parse("SVR GAME MATCH {GAMETYPE: \"Tic-tac-toe\", PLAYERTOMOVE: \"TestPlayer\", OPPONENT: \"TestOpponent\"}");
        assertTrue(iev instanceof MatchEvent);
        MatchEvent mev = (MatchEvent) iev;
        assertEquals(mev.gameType, "Tic-tac-toe");
        assertEquals(mev.playerToMove, "TestPlayer");
        assertEquals(mev.opponent, "TestOpponent");
    }

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
