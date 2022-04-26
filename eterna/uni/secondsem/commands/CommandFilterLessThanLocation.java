package eterna.uni.secondsem.commands;

import java.util.List;
import java.util.stream.Collectors;

import eterna.uni.secondsem.Location;
import eterna.uni.secondsem.networking.ServerResponse;
import eterna.uni.secondsem.networking.ServerResponseMessage;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandFilterLessThanLocation extends Command {

    private final Location location;

    public CommandFilterLessThanLocation(Location _location) {
        location = _location;
    }

    @Override
    public ServerResponse invoke() {
        List<String> results = ServerInitializer.getCollectionManager().get_list().stream()
                                                                                  .filter(p -> p.get_location()
                                                                                  .compareTo(location) < 0)
                                                                                  .map(p -> p.toString())
                                                                                  .collect(Collectors.toList());

        if (results.size() < 1) {
            return new ServerResponseMessage("No results.");
        } else {
            return new ServerResponseMessage(results.stream().collect(Collectors.joining("\n", "People with location less than " + location, "\n")));
        }
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[] { Location.class };
    }
}
