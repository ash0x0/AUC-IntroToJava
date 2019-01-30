This implementations requires two instances of the same codebase.
In order to function correctly, change the following in MouseSyncClient.java
	private static final String SERVER_NAME to the address of the other peer
	private static final int SERVER_PORT to the port number of the other peer
	private static final int PORT to the port number of this instance
This does require that you have the IP address of the other machine.

Steps are as follows:
1. Get the IP address of the two machines you'll run the two instances on
2. In the first instance, change SERVER_NAME to the IP address of the second machine
3. In the second instance, change SERVER_NAME to the IP address of the first machine
4. In the first instance, change PORT to whatever you like, same in the second instance
5. In the first instance, change SERVER_PORT to the PORT in the second instance
6. In the second instance, change SERVER_PORT to PORT in the first instance

My apologies for all the trouble to setting up a proper connection here, I was working on this alone and didn't have the time to fully implement a LAN discovery service to make it possible for peers to discover each other without user input. I could have implemented a GUI to help with setting up the connection parameters but I was really pressed for time. That being said, if you simply duplicate the codebase submitted in another project and exchange PORT and SERVER_PORT (PORT will be 9100 and SERVER_PORT will be 9200) it'll work once you fire up both instances.
