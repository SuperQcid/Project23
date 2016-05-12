package knoftests;

import knof.command.Command;
import org.junit.Test;
import static org.junit.Assert.*;

public class CommandTest {
    @Test
    public void testFormatting() {
        Command command = Command.CHALLENGE;
        String formatted = command.format("john", "sburb");
        assertEquals("challenge \"john\" \"sburb\"", formatted);
    }
}
