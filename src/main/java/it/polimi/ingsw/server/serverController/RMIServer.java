package it.polimi.ingsw.server.serverController;

import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

/**
 * The RMIServer class implements an RMI server for handling remote client connections.
 * It sets up the RMI registry and binds the remote object to it.
 */
public class RMIServer implements ServerInterface, CommunicationInterface {
    private Registry registry; // RMI registry to bind the remote objects
    private CommunicationInterface serverInterface; // Reference to the server interface
    private String ip; // Server IP address
    private int port;

    /**
     * Constructor: Initializes the RMIServer with IP.
     */
    public RMIServer(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    /**
     * Starts the RMI server by exporting the remote object and binding it to the registry.
     */
    @Override
    public void start() {
        try {
            System.setProperty("java.rmi.server.hostname", ip);
            // Initialize the serverInterface
            serverInterface = new RMIServer(ip, port);

            // Export the remote object and obtain the stub
            CommunicationInterface stub = (CommunicationInterface) UnicastRemoteObject.exportObject(serverInterface, 0);

            // Obtain the existing registry or create a new one on the specified port
            registry = LocateRegistry.createRegistry(port);

            // Bind the stub in the registry with a unique name
            registry.rebind("CommunicationInterface", stub);

            System.out.println("RMI Server started on " + ip + ":" + port + ".");
        } catch (RemoteException e) {
            System.err.println("Another server is already running. Closing this instance...");
            System.exit(0);
        }
    }

    /**
     * Stops the RMI server by unbinding the remote object from the registry and unexporting the remote object.
     */
    @Override
    public void stop() {
        try {
            if (registry != null) {
                // Check if the remote object is bound before unbinding
                try {
                    registry.unbind("CommunicationInterface");
                } catch (NotBoundException e) {
                    System.out.println("Remote object not bound: " + e.getMessage());
                }
                UnicastRemoteObject.unexportObject(serverInterface, true);
            }
            System.out.println("RMI Server stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}