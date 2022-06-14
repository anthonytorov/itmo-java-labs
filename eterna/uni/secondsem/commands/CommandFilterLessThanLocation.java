package eterna.uni.secondsem.commands;

import java.io.Serializable;

import eterna.uni.secondsem.Location;
import eterna.uni.secondsem.Person;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandFilterLessThanLocation extends Command {

    private final Location location;

    public CommandFilterLessThanLocation(Location _location) {
        location = _location;
    }

    @Override
    public Response invoke() {
        Person[] results = ServerInitializer.getCollectionManager().get_list().stream()
                                                                                  .filter(p -> p.get_location().compareTo(location) < 0)
                                                                                  .toArray(Person[]::new);
        return new Response(results, location);
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Location.class };
    }

    public class Response implements Serializable {
        public final Person[] results;
        public final Location promptedLocation;

        public Response(Person[] results, Location location) {
            this.results = results;
            this.promptedLocation = location;
        }
    }
}
