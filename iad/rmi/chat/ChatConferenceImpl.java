package iad.rmi.chat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatConferenceImpl extends UnicastRemoteObject implements ChatConference {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6533643843424425149L;
	final static private Map<String, ChatParticipant> hmNomChat = new ConcurrentHashMap<>();
	private String name;
	private String description;
	private boolean isStarted;
	
	public ChatConferenceImpl(String name, String description) throws RemoteException{
		this.name=name;
		this.description=description;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public String getDescription() throws RemoteException {
		return description;
	}

	@Override
	public boolean isStarted() throws RemoteException {
		// TODO Auto-generated method stub
		return isStarted;
	}

	@Override
	public void addParticipant(ChatParticipant p) throws RemoteException {
		
		this.broadcast(new ChatMessage("Server", p.getName() + " has joined the conference"));
		hmNomChat.put(p.getName(), p);
	}

	@Override
	public void removeParticipant(ChatParticipant p) throws RemoteException {
		this.broadcast(new ChatMessage("Server", p.getName() + " has left the conference"));
		hmNomChat.remove(p.getName());
	}

	@Override
	public String[] participants() throws RemoteException {
		Set<String> keySet = hmNomChat.keySet();
		return keySet.toArray(new String[keySet.size()]);
	}

	@Override
	public void broadcast(ChatMessage message) throws RemoteException {
		hmNomChat.entrySet().parallelStream().forEach((entry)-> {
			ChatParticipant p = entry.getValue();
			try {
				p.process(message);
			} catch (RemoteException e) {
				System.out.println("trying to remove participant");
				try {
					hmNomChat.remove(entry.getKey());
					this.broadcast(new ChatMessage("Server", entry.getKey() + " has left the conference"));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void start() throws RemoteException {
		isStarted = true;
	}

	@Override
	public void stop() throws RemoteException {
		isStarted = false;
	}

}
