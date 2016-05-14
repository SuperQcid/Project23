package knof.model;

import knof.plugin.Plugin;

public class GameSettings {
    private final String name;
    private String ai;

    public GameSettings(String name) {
        this.name = name;
    }

    public void setSelectedAI(String newAi) {
        System.out.println(newAi+" selected");
        this.ai = newAi;
    }

    public String getSelectedAI() {
        return ai;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
