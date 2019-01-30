import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoveryServiceClient extends Thread {

    private static final int PORT = 9100;
    private static final String PACKET = "MOUSE_DOT_DISCOVERY";
    private static final String RESPONSE = "MOUSE_DOT_RESPONSE";
    private static final String CLASS_NAME = DiscoveryServiceClient.class.getSimpleName();
    private static Logger Log = Logger.getLogger(CLASS_NAME);
    private static DiscoveryServiceClient mInstance;
    private Thread mThreadInstance;

    static {
        mInstance = new DiscoveryServiceClient();
    }

    public static DiscoveryServiceClient getInstance() {
        return mInstance;
    }

    InetAddress mPeerAddress;

    public InetAddress getPeerAddress() {
        return this.mPeerAddress;
    }

    @Override
    public void run() {
        // Find the server using UDP broadcast
        try {
            //Open a random port to send the package
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            byte[] sendData = PACKET.getBytes();
            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), PORT);
                socket.send(sendPacket);
                Log.log(Level.INFO, "Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (Exception e) {
                Log.log(Level.SEVERE, null, e);
            }
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
                    // Send the broadcast package!
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, PORT);
                        socket.send(sendPacket);
                    } catch (Exception e) {
                        Log.log(Level.SEVERE, null, e);
                    }
                    Log.log(Level.INFO, "Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }
            Log.log(Level.INFO, "Done looping over all network interfaces. Now waiting for a reply!");
            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(receivePacket);
            //We have a response
            Log.log(Level.INFO, "Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals(RESPONSE)) {
                //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
                Log.log(Level.INFO, receivePacket.getAddress().toString());
                this.mPeerAddress = receivePacket.getAddress();
            }
            //Close the port!
            socket.close();
        } catch (IOException e) {
            Log.log(Level.SEVERE, null, e);
        }
    }

    public void start () {
        if (mThreadInstance == null) {
            mThreadInstance = new Thread (this, CLASS_NAME);
            mThreadInstance.start ();
        }
    }
}
