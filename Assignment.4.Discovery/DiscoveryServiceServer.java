import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoveryServiceServer extends Thread {

    private static final int PORT = 9100;
    private static final String PACKET = "MOUSE_DOT_DISCOVERY";
    private static final String RESPONSE = "MOUSE_DOT_RESPONSE";
    private static final String CLASS_NAME = DiscoveryServiceServer.class.getSimpleName();
    private static Logger Log = Logger.getLogger(CLASS_NAME);
    private static DiscoveryServiceServer mInstance;
    private Thread mThreadInstance;

    static {
        mInstance = new DiscoveryServiceServer();
    }

    public static DiscoveryServiceServer getInstance() {
        return mInstance;
    }

    DatagramSocket socket;

    @Override
    public void run() {
        try {
            //Keep a socket open to listen to all the UDP traffic that is destined for this port
            socket = new DatagramSocket(PORT, InetAddress.getByName("0.0.0.0"));
            socket.setBroadcast(true);
            while (true) {
                Log.log(Level.INFO, "Ready to receive broadcast packets!");
                //Receive a packet
                byte[] recvBuf = new byte[PACKET.getBytes().length];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                //Packet received
                Log.log(Level.INFO, "Discovery packet received from: " + packet.getAddress().getHostAddress());
                Log.log(Level.INFO, "Packet received; data: " + new String(packet.getData()));
                // Broadcast the message over all the network interfaces
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                        continue; // Don't want to broadcast to the loopback interface
                    }
                    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        }
                        Log.log(Level.INFO, "Host " + broadcast.getHostAddress());
                    }
                }
                if (packet.getAddress().equals(socket.getLocalAddress())) {
                    Log.log(Level.INFO, "Packet address same as host, skipping");
                    continue;
                }
                //See if the packet holds the right command (message)
                String message = new String(packet.getData()).trim();
                if (message.equals(PACKET)) {
                    byte[] sendData = RESPONSE.getBytes();
                    //Send a response
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                    socket.send(sendPacket);
                    Log.log(Level.INFO, "Sent packet to: " + sendPacket.getAddress().getHostAddress());
                }
            }
        } catch (IOException ex) {
            Log.log(Level.SEVERE, null, ex);
        }
    }

    public void start () {
        if (mThreadInstance == null) {
            mThreadInstance = new Thread (this, CLASS_NAME);
            mThreadInstance.start ();
        }
    }
}