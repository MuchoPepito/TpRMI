package iad.rmi.chat;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

public class ChatParticipantImpl extends UnicastRemoteObject implements ChatParticipant, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2336068402562281852L;
	private boolean isConnected;
	private String name;
	private ChatConference chatConference;
	private Queue<ChatMessage> queueMessages;
	
	public ChatParticipantImpl(String name) throws RemoteException {
		queueMessages = new LinkedList<>();
		this.name=name;
	}

	@Override
	public boolean join(ChatConference conference) throws RemoteException {
		chatConference = conference;
		chatConference.addParticipant(this);
		isConnected = true;
		return false;
	}

	@Override
	public void leave(ChatConference conference) throws RemoteException {
		conference.removeParticipant(this);
		isConnected = false;
		chatConference = null;
	}

	@Override
	public void send(String txt) throws RemoteException {
		chatConference.broadcast(new ChatMessage(this.getName(), txt));
	}

	@Override
	public void process(ChatMessage msg) throws RemoteException {
		queueMessages.add(msg);
	}

	@Override
	public boolean isConnected() throws RemoteException {
		return isConnected;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public ChatMessage next() throws RemoteException {
		return queueMessages.poll();
	}

}
