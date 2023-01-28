package es.mychat.app.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import es.mychat.app.queue.MessagesQueue;

public class ClientThread extends Thread {
	private Socket socket;
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());

			while (true) {
				System.out.println("connected");
				output.writeUTF("connected");
				
				String messageReceived = input.readUTF();
				
				System.out.println(messageReceived);
				MessagesQueue.saveMessage(messageReceived);
				
				output.writeUTF("message received");
			}
		} catch (Exception e) {
			System.out.println("Error in thread: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
