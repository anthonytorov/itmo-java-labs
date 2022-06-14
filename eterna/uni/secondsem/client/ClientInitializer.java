package eterna.uni.secondsem.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

import eterna.uni.secondsem.CommandReader;
import eterna.uni.secondsem.FailedToInitializeException;
import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.commands.Command;
import eterna.uni.secondsem.commands.CommandExit;
import eterna.uni.secondsem.networking.NetworkSettings;

public class ClientInitializer {
    private static ClientInitializer instance;

    public static ClientInitializer get_instance() {
        return instance;
    }

    private final CommandReader commandReader;
    private boolean run;

    public static void start(String hostname, int port) { 
        instance = new ClientInitializer(hostname, port);
        instance.executionLoop(); 
    }

    private ClientInitializer(String hostname, int port) {
        
        try {
            NetworkSettings.initialize_hostname(hostname, port, 4096);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }  

        commandReader = new CommandReader(System.in, true);
        LogPrinter.initialize();
    }

    private void executionLoop() {
        run = true;
        LogPrinter.log("Attempting connection to " + NetworkSettings.get_address() + ":" + NetworkSettings.get_port());

        SocketChannel socketChannel = attemptConnection(0);
        if (socketChannel == null) {
            LogPrinter.log("Failed to connect.");
            return;
        }
        LogPrinter.log("Connection successful.");

        try {
            ClientObjectSerializer noe = new ClientObjectSerializer(socketChannel);

            while (run && commandReader.canReadNextCommand()) {
    
                Command command = commandReader.readCommand();
    
                if (command == null) {
                    LogPrinter.log("Command not recognised. Use \"help\" to see available commands.");
                } else if (command.getClass() == CommandExit.class) {
                    run = false;
                } else {
                    noe.pushObject(command);
                    Object response = noe.readResponse();
                    ClientResponseHandler.handleResponse(command.getClass().getName(), response);
                }
            }
    
            noe.closeSocket();
        } catch (FailedToInitializeException e) {
            return;
        }
    }

    private SocketChannel attemptConnection(int height) {
        
        if (height == 10) return null;
        
        try {
            return SocketChannel.open(new InetSocketAddress(NetworkSettings.get_address(), NetworkSettings.get_port()));
        } catch (IOException e) {
            LogPrinter.log("Failed to connect, retrying...");
            return attemptConnection(height + 1);
        }
    }
}
