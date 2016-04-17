package knof.command;

import java.util.Formatter;

public enum Command {
    LOGIN("login %s"),
    GET_PLAYERLIST("get playerlist"),
    GET_GAMELIST("get gamelist"),
    CHALLENGE("challenge \"%s\" \"%s\"");

    private final String format;

    Command(String format) {
        this.format = format;
    }

    public String format(Object... arguments) {
        return String.format(this.format, arguments);
    }
}
