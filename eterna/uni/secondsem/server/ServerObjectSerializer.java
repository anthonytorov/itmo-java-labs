package eterna.uni.secondsem.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerObjectSerializer {

    private Socket _socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public ServerObjectSerializer(Socket socket) {
        _socket = socket;

        try {
            outStream = new ObjectOutputStream(_socket.getOutputStream());
            inStream = new ObjectInputStream(_socket.getInputStream());
        } catch (IOException e) {
        }
    }

    public void pushObject(Object object) {

        try {
            outStream.writeObject(object);
            outStream.flush();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public Object readResponse() {

        Object response = null;

        try {
            response = inStream.readObject();
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
