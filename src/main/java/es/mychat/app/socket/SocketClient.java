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
		System.out.println("Write all the messages that you want:");
		System.out.println("==================================================================");
		try {
			socket = new Socket(HOST, PORT);

			Scanner scanner = new Scanner(System.in);;
			while (!"exit".equals(text)) {
				text = scanner.nextLine();

				DataInputStream input = new DataInputStream(socket.getInputStream());
				String connectedMessage = input.readUTF(); //receive the message from server that confirm that client is connected
				
				if ("connected".equals(connectedMessage)) {
					DataOutputStream message = new DataOutputStream(socket.getOutputStream());
					
					message.writeUTF(text); //send the text to the server
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
