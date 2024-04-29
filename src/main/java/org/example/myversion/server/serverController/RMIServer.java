package org.example.myversion.server.serverController;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

import org.example.myversion.server.Server;
import org.example.myversion.server.serverController.ServerInterface;

public class RMIServer implements ServerInterface {
    private int port;
    private Registry registry;

    private RMIServer server;//glielo devo passare dalla classe server o lo creo qui???

    private static final int RMI_PORT = 1000;

    public RMIServer(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        try {

            // Esporto l'oggetto remoto e ottieni il riferimento allo stub
            MyRemote stub = (MyRemote) UnicastRemoteObject.exportObject(remoteObj, 0);

            // Ottieni il registro RMI esistente o crea uno nuovo sulla porta specificata
            registry = LocateRegistry.createRegistry(port);

            // Pubblica lo stub nell'ambiente di esecuzione
            registry.rebind("il mio oggetto remoto", stub);

            System.out.println("RMI Server initialized on port " + port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            // Rimuovi il binding dello stub dal registro RMI
            if (registry != null) {
                registry.unbind("MyRemote");
            }
            System.out.println("RMI Server stopped.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}