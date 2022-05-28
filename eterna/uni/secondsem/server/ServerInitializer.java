package eterna.uni.secondsem.server;

import java.net.UnknownHostException;

import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.networking.NetworkSettings;

public class ServerInitializer {
    private static ServerInitializer instance;

    public static ServerInitializer get_instance() {
        return instance;
    }

    public static void start(String _path, int port) { 
        instance = new ServerInitializer(_path, port); 

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
    private ServerObjectSerializer activeNoe;

    public static void exit() { instance.run = false; instance.activeNoe.closeSocket(); }

    public ServerInitializer(String _databasePath, int port) {
        
        try {
            NetworkSettings.initialize_hostname("localhost", port, 4096);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        LogPrinter.initialize();

        collectionManager = new CollectionManager(_databasePath);

        connectionReceiver = new ServerConnectionReceiver();
    }

    private void executionLoop() {

        run = true;

        while (run) {
            activeNoe = connectionReceiver.waitForConnection();
            commandInvoker = new ServerCommandInvoker(activeNoe);

            while (activeNoe.isConnectionOpen()) {
                boolean success = commandInvoker.executeNextClientCommand();
                if (!success) {
                    activeNoe.closeSocket();
                    LogPrinter.log("Closing connection...");
                }
            }
        }

        collectionManager.save();
    }
}
