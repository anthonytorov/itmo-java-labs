package eterna.uni.secondsem.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import eterna.uni.secondsem.LogPrinter;
import eterna.uni.secondsem.networking.NetworkSettings;

public class ServerConnectionReceiver {

    private ServerSocket _serverSocket; 

    public ServerConnectionReceiver() {
        try {
            _serverSocket = new ServerSocket(NetworkSettings.get_port());
            _serverSocket.setReceiveBufferSize(NetworkSettings.get_bufferSize());

            LogPrinter.log("Started database server on port " + _serverSocket.getLocalPort());
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public Socket waitForConnection() {
        try {
            LogPrinter.log("Waiting for client...");
            Socket clientSocket = _serverSocket.accept();
            LogPrinter.log("Accepted client " + clientSocket.getRemoteSocketAddress());
            return clientSocket;
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return null;
    }

    public void closeSocket() {
        try {
            _serverSocket.close();
        }
        catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }
}
