package eterna.uni.secondsem.networking;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkSettings {
    
    public static NetworkSettings main;
    public static void initialize_localhost() throws UnknownHostException {
        main = new NetworkSettings(InetAddress.getLocalHost(), 3819, 8096);
    }
    public static void initialize(String _ipV4String, int _port, int _bufferSize) throws UnknownHostException {
        main = new NetworkSettings(convertFromString(_ipV4String), _port, _bufferSize);
    }

    private final int port;
    public static int get_port() { return main.port; }

    private final int bufferSize;
    public static int get_bufferSize() { return main.bufferSize; }

    private final InetAddress ipAddress;
    public static InetAddress get_address() {
        if (main.ipAddress != null) {
            return main.ipAddress;
        }
        throw new NullPointerException("Address is null");
    }

    public NetworkSettings(InetAddress _ipAddress, int _port, int _bufferSize) {
        port = _port;
        ipAddress = _ipAddress;
        bufferSize = _bufferSize;
    }

    private static InetAddress convertFromString(String _ipv4String) throws UnknownHostException {
        String[] nums = _ipv4String.split(".");
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = Byte.parseByte(nums[i]);
        }
        
        return InetAddress.getByAddress(bytes);
    }
}
