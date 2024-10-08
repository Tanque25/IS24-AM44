package it.polimi.ingsw.server.serverController;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface ServerInterface {
    /**
     * Starts the server.
     *
     * @throws RemoteException       if a connection error occurs
     * @throws MalformedURLException if the url of the server is not valid
     */
    void start() throws RemoteException, MalformedURLException;

    /**
     * Stops the server.
     *
     * @throws RemoteException   if a connection error occurs
     * @throws NotBoundException if the server is not bound
     */
    void stop() throws RemoteException, NotBoundException;
}
