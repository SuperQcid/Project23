package knoftests.command;

import knof.command.Command;
import knof.connection.CommandHandler;
import knof.event.EventSystem;
import knof.event.events.StatusEvent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.io.StringWriter;

public class HandlerTest {
    StringWriter writer;
    PrintWriter out;
    EventSystem eventSystem;
    CommandHandler handler;

    @Before
    public void setUp() {
        writer = new StringWriter();
        out = new PrintWriter(writer);
        eventSystem = new EventSystem();
        handler = new CommandHandler(eventSystem, out);
    }
}
