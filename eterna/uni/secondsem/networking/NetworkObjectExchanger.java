package eterna.uni.secondsem.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkObjectExchanger {

    private Socket _socket;

    public NetworkObjectExchanger(Socket socket) {
        _socket = socket;
    }

    public void pushObject(Object object) {

        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(_socket.getOutputStream());
            objectOut.writeObject(object);
            objectOut.flush();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public Object readResponse() {

        Object response = null;

        try {
            ObjectInputStream objectIn = new ObjectInputStream(_socket.getInputStream());
            response = objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    public boolean isConnectionOpen() {
        return !_socket.isClosed();
    }

    public void closeSocket() {
        try {
            _socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
