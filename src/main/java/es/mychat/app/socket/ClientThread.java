package es.mychat.app.socket;

import java.io.DataInputStream;
import java.net.Socket;

import es.mychat.app.model.entity.ClientEntity;

public class ClientThread extends Thread {
	private Socket socket;
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("new thread created for a client");
		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());

			while (true) {
				System.out.println("connected");
				
				String messageReceived = input.readUTF(); //message received from the client
				
				System.out.println(messageReceived);
				
				for (ClientEntity client : SocketServer.CLIENTS_CONNECTED) {
					client.getOutput().writeUTF(getNickFromMessage(messageReceived) + ">> " + getContentFromMessage(messageReceived));
				}
			}
		} catch (Exception e) {
			System.out.println("Error in thread: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private String getNickFromMessage(String message) {
		return message.split("\\|")[0];
	}
	
	private String getContentFromMessage(String message) {
		return message.split("\\|")[1];
	}
}
