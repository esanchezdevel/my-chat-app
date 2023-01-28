package es.mychat.app.socket;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	private static final int PORT = 5000;
		
	public static void init() {

		Socket socket = null;
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(PORT);
			
			socket = new Socket();
			
			System.out.println("connection with client confirmed");
			
			while(true) {
				System.out.println("waiting for a connection...");
				socket = serverSocket.accept();
				
				new ClientThread(socket).start();
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
