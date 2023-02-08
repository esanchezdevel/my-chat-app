package es.mychat.app.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			while (true) {
				System.out.println("connected");
				
				String messageReceived = input.readLine();
				
				if ("exit".equals(getContentFromMessage(messageReceived))) {
					SocketServer.CLIENTS_CONNECTED.removeIf(client -> client.getId().equals(getNickFromMessage(messageReceived)));
					SocketServer.CLIENTS_CONNECTED.forEach(client -> {
						try {
							PrintWriter output = new PrintWriter(client.getSocket().getOutputStream());
				            output.write("<------" + getNickFromMessage(messageReceived) + " left the session------>\n");
				            output.flush();
						} catch (IOException e) {
							System.out.println("Error sending bye message to all clients");
						}
					});
				} else {
					System.out.println(messageReceived);
					
					for (ClientEntity client : SocketServer.CLIENTS_CONNECTED) {
						PrintWriter output = new PrintWriter(client.getSocket().getOutputStream());
			            output.write(getNickFromMessage(messageReceived) + ">> " + getContentFromMessage(messageReceived) + "\n");
			            output.flush();
					}
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
