package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.server.ServerInitializer;

public class CommandRemove extends Command {

    private final Integer id;

    public CommandRemove(Integer _id) {
        id = _id;
    }

    @Override
    public Integer invoke() {
        if (ServerInitializer.getCollectionManager().remove(id)) {
            return id;
        } else {
            return -1;
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Integer.class };
    }
}
