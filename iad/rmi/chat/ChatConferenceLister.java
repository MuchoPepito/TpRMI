package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatConferenceLister {

	public static void main(String[] args) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry(args[0], 1099);
		String[] remoteRefs = registry.list();
		for(String ref : remoteRefs)
			System.out.println(ref);
	}

}
