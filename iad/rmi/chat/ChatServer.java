package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {

	public static void main(String[] args) throws RemoteException{
		Registry registry = LocateRegistry.createRegistry(1099);
		ChatConference conf = new ChatConferenceImpl("bestConfEver","petite description oklm");
		registry.rebind(conf.getName(), conf);
	}
	


}
