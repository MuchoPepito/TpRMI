package iad.rmi.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.List;

public class ChatClient {

	public static void main(String[] args) throws NotBoundException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		Registry registry = LocateRegistry.getRegistry(args[0], 1099);
		List<String> remoteRefs = Arrays.asList(registry.list());
		for(String ref : remoteRefs)
			System.out.println(ref);
		String input;
		do{
			System.out.println("Conf to join : ");
			input = br.readLine();
			System.out.println(input);
		}while(!remoteRefs.contains(input));
		ChatConference conf = (ChatConference) registry.lookup(input);
		String nameInput;
		do{
			System.out.println("Name : ");
			nameInput = br.readLine();
		}while(nameInput == null);
		ChatParticipant chatParticipant = new ChatParticipantImpl(nameInput);
		chatParticipant.join(conf);
		ChatClientConsole chatConsole = new ChatClientConsole(conf, chatParticipant);
		chatConsole.start();
		int i=0;
		while(true)
			chatParticipant.send(""+i++);
	}
	
}
