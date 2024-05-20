package org.example.myversion.server.serverController;

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

    /**
     * Constructor: Initializes the RMIServer.
     */
    public RMIServer() {
        super();
    }

    /**
     * Starts the RMI server by exporting the remote object and binding it to the registry.
     */
    @Override
    public void start() {
        try {
            // TODO: understand if writing 'serverInterface = this;' would be more correct
            // Initialize the serverInterface
            serverInterface = new RMIServer();

            // Export the remote object and obtain the stub
            CommunicationInterface stub = (CommunicationInterface) UnicastRemoteObject.exportObject(serverInterface, 0);

            // Obtain the existing registry or create a new one on the specified port
            registry = LocateRegistry.createRegistry(RMI_PORT);

            // Bind the stub in the registry with a unique name
            registry.rebind("MyRemoteObject", stub);

            System.out.println("RMI Server started on port " + RMI_PORT + ".");
        } catch (RemoteException e) {
            System.err.println("Another server is already running. Closing this instance...");
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Stops the RMI server by unbinding the remote object from the registry and unexporting the remote object.
     */
    @Override
    public void stop() {
        try {
            // Remove the binding of the stub from the RMI registry
            if (registry != null) {
                registry.unbind("MyRemoteObject");
                UnicastRemoteObject.unexportObject(serverInterface, true);
            }
            System.out.println("RMI Server stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}