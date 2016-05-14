package knof.model;

import knof.app.KnofApplication;
import knof.plugin.Plugin;

public class GameSettings {
    private final String name;
    private Plugin plugin;

    public GameSettings(String name) {
        this.name = name;
        this.plugin = KnofApplication.getPlugin(name);
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
