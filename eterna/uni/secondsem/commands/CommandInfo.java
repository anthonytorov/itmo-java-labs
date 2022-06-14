package eterna.uni.secondsem.commands;

import java.io.Serializable;
import java.util.Date;

import eterna.uni.secondsem.server.CollectionManager;
import eterna.uni.secondsem.server.ServerInitializer;

public class CommandInfo extends Command {

    @Override
    public Response invoke() {
        CollectionManager collectionManager = ServerInitializer.getCollectionManager();
        return new Response(
            collectionManager.get_list().getClass(),
            collectionManager.get_initializationDate(),
            collectionManager.get_list().size()
        );
    }

    public static Class<?>[] getConstuctorClasses() {
        return new Class<?>[0];
    }

    public class Response implements Serializable {
        public final Class<?> collectionType;
        public final Date initDate;
        public final int size;

        public Response(Class<?> type, Date initDate, int size) {
            collectionType = type;
            this.initDate = initDate;
            this.size = size;
        }
    }
}
