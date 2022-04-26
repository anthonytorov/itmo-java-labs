package eterna.uni.secondsem.commands;

import eterna.uni.secondsem.Color;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandCountByHairColor extends Command{

    private final Color selectedColor; 

    public CommandCountByHairColor(Color color) {
        selectedColor = color;
    }

    @Override
    public ServerResponse invoke() {
        long count = ServerInitializer.getCollectionManager().get_list().stream().filter(p -> p.get_hairColor() == selectedColor).count();
        return new ServerResponseMessage("People with hair color " + selectedColor + ": " + count);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Color.class };
    }
}
