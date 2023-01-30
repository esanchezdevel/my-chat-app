package es.mychat.app.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import es.mychat.app.model.entity.ClientEntity;
import es.mychat.app.queue.MessagesQueue;

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
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			while (true) {
				System.out.println("connected");
				
				String messageReceived = input.readUTF(); //message received from the client
				
				System.out.println(messageReceived);
				MessagesQueue.saveMessage(messageReceived);
				
				for (ClientEntity client : SocketServer.CLIENTS_CONNECTED) {
					client.getOutput().writeUTF(">> " + messageReceived);
				}
			}
		} catch (Exception e) {
			System.out.println("Error in thread: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
