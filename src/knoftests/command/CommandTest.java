package knoftests.command;

import knof.command.Command;
import org.junit.Test;

import java.util.MissingFormatArgumentException;

import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void testFormatting() {
        Command command = Command.CHALLENGE;
        String formatted = command.format("john", "sburb");
        assertEquals("challenge \"john\" \"sburb\"", formatted);
    }

    @Test(expected = MissingFormatArgumentException.class)
    public void testIncorrectFormatting() {
        Command command = Command.LOGIN;
        command.format();
    }
}
