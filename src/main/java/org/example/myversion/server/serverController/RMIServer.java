package org.example.myversion.server.serverController;

import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


import org.example.myversion.server.serverController.RMIInterface;
import org.example.myversion.server.serverController.ServerInterface;

public class RMIServer extends RMIInterface implements ServerInterface {
    private int port;
    private Registry registry;

    private RMIInterface serverInterface;//glielo devo passare dalla classe server o lo creo qui???


    public RMIServer(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        try {
             //interfaccia non puo essere inizializzata??
            serverInterface=new RMIServer(70);// Esporto l'oggetto remoto e ottieni il riferimento allo stub

            RMIInterface stub = (RMIInterface) UnicastRemoteObject.exportObject(serverInterface, 0);

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