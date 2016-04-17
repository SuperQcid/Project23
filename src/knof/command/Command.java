package knof.command;

public enum Command {
    LOGIN("login", new ArgumentField(String.class)),
    GET_PLAYERLIST("get playerlist"),
    GET_GAMELIST("get gamelist"),
    CHALLENGE("challenge", new ArgumentField(String.class, true), new ArgumentField(String.class, true));

    private final ArgumentField[] fields;
    private final String prefix;

    Command(String prefix, ArgumentField... fields) {
        this.prefix = prefix;
        this.fields = fields;
    }

    static class ArgumentField {
        private final IFormatter formatter;
        private final Class fieldClass;

        public ArgumentField(Class fieldClass) {
            this(fieldClass, false);
        }

        public ArgumentField(Class<String> fieldClass, boolean quoted) {
            this.fieldClass = fieldClass;
            this.formatter = quoted?(Object obj) -> "\"" + obj + "\"":Object::toString;
        }

        public String format(Object obj) {
            return this.formatter.format(obj);
        }
    }

    public String format(Object... arguments) {
        if(this.fields.length==arguments.length) {
            String message = this.prefix;
            for(int i=0; i<this.fields.length; i++) {
                message += " " + this.fields[0].format(arguments[i]);
            }
            return message;
        }
        System.err.println("Wrong number of arguments to command!");
        return ""; // Do nothing then?
    }

    @FunctionalInterface
    private interface IFormatter {
        String format(Object obj);
    }

}
