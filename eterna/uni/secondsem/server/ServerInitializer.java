package eterna.uni.secondsem.server;

import java.net.UnknownHostException;

import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.networking.NetworkObjectExchanger;
import eterna.uni.secondsem.networking.NetworkSettings;

public class ServerInitializer {
    private static ServerInitializer instance;

    public static ServerInitializer get_instance() {
        return instance;
    }

    public static void start(String _path) { 
        instance = new ServerInitializer(_path); 

        instance.executionLoop();
    }

    public static ServerConnectionReceiver getConnectionReceiver() {
        return instance.connectionReceiver;
    }

    public static ServerCommandInvoker getCommandInvoker() {
        return instance.commandInvoker;
    }

    public static CollectionManager getCollectionManager() {
        return instance.collectionManager;
    }

    private final CollectionManager collectionManager;
    private final ServerConnectionReceiver connectionReceiver;

    private ServerCommandInvoker commandInvoker;
    private boolean run;

    public ServerInitializer(String _databasePath) {
        
        try {
            NetworkSettings.initialize_localhost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        collectionManager = new CollectionManager(_databasePath);

        connectionReceiver = new ServerConnectionReceiver();
    }

    private void executionLoop() {

        run = true;

        while (run) {
            NetworkObjectExchanger noe = connectionReceiver.waitForConnection();
            commandInvoker = new ServerCommandInvoker(noe);

            while (noe.isConnectionOpen()) {
                boolean success = commandInvoker.executeNextClientCommand();
                if (!success) {
                    noe.closeSocket();
                    LogPrinter.log("Closing connection...");
                }
            }
        }

        collectionManager.save();
    }
}
