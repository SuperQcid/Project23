package knof.model;

import knof.app.KnofApplication;
import knof.plugin.Plugin;

public class GameEntry {
    private final String name;
    public final Server server;
    private Plugin plugin;

    public GameEntry(String name, Server server) {
        this.name = name;
        this.server = server;
        this.plugin = server.getPlugin(name);
    }

    public void setSelectedAI(String newAi) {
        System.out.println(newAi+" selected");
        this.plugin.selectAI(newAi);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean hasPlugin() {
        return plugin!=null;
    }
}
