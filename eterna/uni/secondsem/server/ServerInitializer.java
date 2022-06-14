package eterna.uni.secondsem.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import eterna.uni.secondsem.FailedToInitializeException;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.commands.Command;
import eterna.uni.secondsem.networking.NetworkSettings;

public class ServerInitializer {
    private static ServerInitializer instance;

    public static ServerInitializer get_instance() {
        return instance;
    }

    public static void start(String _path, int port) {
        try {
            instance = new ServerInitializer(_path, port);
            instance.executionLoop();
        } catch (FailedToInitializeException ex) {
            LogPrinter.log("Could not initialize due to: " + ex.cause);
        }
    }

    public static ServerConnectionReceiver getConnectionReceiver() {
        return instance.connectionReceiver;
    }

    public static CollectionManager getCollectionManager() {
        return instance.collectionManager;
    }

    private final CollectionManager collectionManager;
    private final ServerConnectionReceiver connectionReceiver;

    private boolean run;

    public static void exit() {
        instance.run = false;
    }

    public ServerInitializer(String _propertiesPath, int port) throws FailedToInitializeException {

        try {
            NetworkSettings.initialize_hostname("localhost", port, 4096);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        LogPrinter.initialize();

        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(_propertiesPath);
            properties.load(in);
            in.close();
        } catch (IOException ex) {
            throw new FailedToInitializeException(ex);
        }
        collectionManager = new CollectionManager(properties);

        connectionReceiver = new ServerConnectionReceiver();
    }

    private void executionLoop() {
        run = true;
        while (run) {
            Socket newConnection = connectionReceiver.waitForConnection();
            
            try {
                Server server = new Server(newConnection);
                while (!server.isConnectionClosed()) {
                    server.serve();
                }
            } catch (FailedToInitializeException ex) {
                LogPrinter.log("Failed to initialize: " + ex.cause);
                run = false;
            } catch (ServerException e) {
                LogPrinter.log("Encountered server exception: " + e);
                run = false;
            } finally {
                LogPrinter.log("Closing connection...");
                collectionManager.save();
            }
        }
    }

    private class Server {
        private final Socket _servingClient;
        private final ObjectInputStream _inStream;
        private final ObjectOutputStream _outStream;

        public Server(Socket servingClient) throws FailedToInitializeException {
            _servingClient = servingClient;
            try {
                LogPrinter.log("Trying to create a server for user " + servingClient.getInetAddress());                
                
                // Create output stream
                _outStream = new ObjectOutputStream(servingClient.getOutputStream());
                _outStream.flush();
                // Wait for output stream to open on the client
                _inStream = new ObjectInputStream(servingClient.getInputStream());
                
                LogPrinter.log("Created a server for user " + servingClient.getInetAddress());                
            } catch (IOException e) {
                throw new FailedToInitializeException(e);
            }
        }

        public void serve() throws ServerException {
            Command command = (Command)readRequest();
            LogPrinter.log("Received a command from client: " + command.getClass().toString());

            Object response = command.invoke();
            
            if (response != null) {
                LogPrinter.log("Executed command, sending response.");
                pushObject(response);
            }
        }

        public boolean isConnectionClosed() { return _servingClient.isClosed(); }

        public void pushObject(Object object) {
    
            try {
                _outStream.writeObject(object);
                _outStream.flush();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        }

        public Object readRequest() throws ServerException {
            try {
                return _inStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new ServerException(e);
            }
        }
    }
}
