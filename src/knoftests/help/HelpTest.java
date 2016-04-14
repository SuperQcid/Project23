package knoftests.help;

import org.junit.Test;
import knof.help.Help;
import static org.junit.Assert.assertNotNull;

public class HelpTest {

	@Test
    public void testHelp() {
        Help help = new Help();
        assertNotNull(help.getPaths());
        assertNotNull(help.getText(help.getPaths().get(0)));
    }
}
