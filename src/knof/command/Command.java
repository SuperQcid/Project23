package knof.command;

public enum Command {
    LOGIN("login", false),
    GET_PLAYERLIST("get playerlist"),
    GET_GAMELIST("get gamelist"),
    CHALLENGE("challenge", true, true);

    private final IFormatter[] formatters;
    private final String prefix;

    Command(String prefix, boolean... quoted) {
        this.prefix = prefix;
        this.formatters = new IFormatter[quoted.length];
        for (int i=0; i<formatters.length; i++) {
            this.formatters[i] = quoted[i]?(Object obj) -> "\"" + obj + "\"":Object::toString;
        }
    }

    public String format(Object... arguments) {
        if(this.formatters.length==arguments.length) {
            String message = this.prefix;
            for(int i=0; i<this.formatters.length; i++) {
                message += " " + this.formatters[0].format(arguments[i]);
            }
            return message;
        }
        System.err.println("Wrong number of arguments for " + this.toString() + "!");
        return ""; // Do nothing then?
    }

    @FunctionalInterface
    private interface IFormatter {
        String format(Object obj);
    }

}
