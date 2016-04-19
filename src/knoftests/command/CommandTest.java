package knoftests.command;

import knof.command.Command;
import org.junit.Test;

import java.util.MissingFormatArgumentException;

import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void formattingTest() {
        Command command = Command.CHALLENGE;
        String formatted = command.format("john", "sburb");
        assertEquals("challenge \"john\" \"sburb\"", formatted);
    }

    @Test(expected = MissingFormatArgumentException.class)
    public void testIncorrectFormatting() {
        Command command = Command.LOGIN;
        command.format();
    }

    @Test
    public void loginTest(){
        Command command = Command.LOGIN;
        String formatted = command.format("mark");
        assertEquals("login mark", formatted);
    }

    @Test
    public void messageTest(){
        Command command = Command.MESSAGE;
        String formatted = command.format("mark", "dit is mijn bericht");
        assertEquals("message \"mark\" dit is mijn bericht", formatted);
    }

    @Test
    public void subscribeTest(){
        Command command = Command.SUBSCRIBE;
        String formatted = command.format("Sudoku");
        System.out.println(formatted);
        assertEquals("subscribe Sudoku",formatted);
    }

    @Test
    public void moveTest(){
        Command command = Command.MOVE;
        String formatted = command.format("test");
        assertEquals("move test",formatted);
    }

    @Test
    public void challengeTest(){
        Command command = Command.CHALLENGE;
        String formatted = command.format("Mark", "Sudoku");
        assertEquals("challenge \"Mark\" \"Sudoku\"",formatted);
    }

    @Test
    public void challengeAcceptTest(){
        Command command = Command.CHALLENGE_ACCEPT;
        String formatted = command.format(42);
        assertArrayEquals("challenge accept 42", formatted);
    }

    @Test
    public void helpTest(){
        Command command = Command.HELP;
        String formatted = command.format("menu");
        assertEquals("help menu", formatted);
    }


}
