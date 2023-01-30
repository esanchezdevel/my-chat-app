package es.mychat.app.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
		
		String text = null;
		
		System.out.println("Welcome to the socket client. To close the connection send \"exit\"");
		System.out.println("Write all the messages that you want:");
		System.out.println("==================================================================");
		try {
			final Socket socket = new Socket(HOST, PORT);

			new Thread() {
				
				@Override
				public void run() {
					while (true) {
						DataInputStream input = null;
						try {
							input = new DataInputStream(socket.getInputStream());
						
							String connectedMessage = null;
							connectedMessage = input.readUTF();
							System.out.println(connectedMessage);
						} catch (IOException e) {
							System.out.println("Error reading messages from server: " + e.getMessage());
							e.printStackTrace();
						}
					}
				};
			}.start();
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("nick: ");
			String nick = scanner.nextLine();
			
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(nick);
			
			System.out.println("Bienvenido " + nick + ". Ahora puedes escribir en el chat.");
			while (!"exit".equals(text)) {
				System.out.print(">" + nick + ": ");
				text = scanner.nextLine();
				
				output.writeUTF(text); //send the text to the server
			}
			scanner.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
