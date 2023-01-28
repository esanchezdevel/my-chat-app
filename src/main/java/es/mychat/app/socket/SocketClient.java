package es.mychat.app.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * @author esanchez
 *
 * To run the socket client execute the following commands:
 * 	$ cd /home/esanchez/workspace/my-chat-app/src/main/java
 *	$ javac es/mychat/app/socket/SocketClient.java
 * 	$ java -cp /home/esanchez/workspace/my-chat-app/src/main/java/ es.mychat.app.socket.SocketClient
 */
public class SocketClient {

	private static final String HOST = "localhost";
	private static final int PORT = 5000;
	
	public static void main(String[] args) {
		
		Socket socket = null;
		
		String text = null;
		
		System.out.println("Welcome to the socket client. To close the connection send \"exit\"");
		
		try {
			socket = new Socket(HOST, PORT);

			Scanner scanner = new Scanner(System.in);;
			while (!"exit".equals(text)) {
				System.out.println("requesting message to user");
				text = scanner.nextLine();

				System.out.println("text to send: " + text);
				DataInputStream input = new DataInputStream(socket.getInputStream());
				System.out.println("TEST-input created from socket");
				String connectedMessage = input.readUTF();
				
				System.out.println("trying to send message: " + text + " - connectedMessage: " + connectedMessage);
				if ("connected".equals(connectedMessage)) {
					DataOutputStream message = new DataOutputStream(socket.getOutputStream());
					
					message.writeUTF(text);

					if ("message received".equals(input.readUTF())) {
						System.out.println("message sent");		
					} else {
						System.out.println("message not sent");
					}
				} else {
					System.out.println("connection not established");
				}
			}
			scanner.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				System.out.println("connection with server closed");
			} catch (Exception e) {
				System.out.println("Error closing connection with server: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
