package knoftests.plugin;

import org.junit.Test;

import knof.plugin.Plugin;
import knof.plugin.PluginLoader;

import static org.junit.Assert.assertNotNull;

public class PluginTest {

    @Test
    public void testPluginLoader() {
        Plugin plugin = PluginLoader.loadPlugin("Reversi"); //TODO: Replace with test plugin
        assertNotNull(plugin);
        //System.out.println(plugin.boardToString());
    }
}
