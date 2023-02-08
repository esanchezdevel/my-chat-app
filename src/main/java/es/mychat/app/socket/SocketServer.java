package es.mychat.app.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import es.mychat.app.model.entity.ClientEntity;

public class SocketServer {

	private static final int PORT = 5000;
	
	public static final List<ClientEntity> CLIENTS_CONNECTED = new ArrayList<>();
	
	public static void init() {

		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		
		try {
			serverSocket = new ServerSocket(PORT);
			
			while(true) {
				System.out.println("waiting for a client to connect...");
				clientSocket = serverSocket.accept();
				
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String nick = input.readLine();
				
				CLIENTS_CONNECTED.add(new ClientEntity(nick, clientSocket, new DataOutputStream(clientSocket.getOutputStream())));
				
				System.out.println("client \"" + nick + "\" connected");
				new ClientThread(clientSocket).start();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
				System.out.println("connection closed");
			} catch(Exception e) {
				System.out.println("Error closing connection: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
