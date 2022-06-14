package eterna.uni.secondsem.commands;

import java.io.Serializable;

import eterna.uni.secondsem.Color;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandCountByHairColor extends Command{

    private final Color selectedColor; 

    public CommandCountByHairColor(Color color) {
        selectedColor = color;
    }

    @Override
    public Response invoke() {
        long count = ServerInitializer.getCollectionManager().get_list().stream().filter(p -> p.get_hairColor() == selectedColor).count();
        return new Response(count, selectedColor);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Color.class };
    }

    public class Response implements Serializable {
        public final long count;
        public final Color color;

        public Response(long count, Color color) {
            this.count = count;
            this.color = color;
        }
    }
}
