package eterna.uni.secondsem.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

import eterna.uni.secondsem.FailedToInitializeException;

public class ClientObjectSerializer {
    private SocketChannel _socketChannel;
    private final ObjectInputStream _inStream;
    private final ObjectOutputStream _outStream;

    public ClientObjectSerializer(SocketChannel socketChannel) throws FailedToInitializeException {
        _socketChannel = socketChannel;
        try {
            // Create an output stream
            _outStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            _outStream.flush();

            // Wait for output to open on the server
            _inStream = new ObjectInputStream(_socketChannel.socket().getInputStream());
        } catch (IOException e) {
            throw new FailedToInitializeException(e);
        }
    }
    
    public void pushObject(Object object) {

        try {
            _outStream.writeObject(object);
            _outStream.flush();
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
    }

    public Object readResponse() {

        Object response = null;

        try {
            response = _inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    public boolean isConnectionOpen() {
        return _socketChannel.isOpen();
    }

    public void closeSocket() {
        try {
            _socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
