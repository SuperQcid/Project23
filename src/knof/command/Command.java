package knof.command;

public enum Command {
    LOGIN("login %s"),
    LOGOUT("logout"),
    MESSAGE("message \"%s\" %s"),
    GET_PLAYERLIST("get playerlist"),
    GET_GAMELIST("get gamelist"),
    SUBSCRIBE("subscribe %s"),
    UNSUBSCRIBE("unsubscribe"),
    MOVE("move %s"),
    CHALLENGE("challenge \"%s\" \"%s\""),
    CHALLENGE_ACCEPT("challenge accept %s"),
    CHALLENGE_FORFEIT("forfeit"),
    HELP("help %s");

    private final String format;

    Command(String format) {
        this.format = format;
    }

    public String format(Object... arguments) {
        return String.format(this.format, arguments);
    }
}
