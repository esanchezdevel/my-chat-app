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

			//Thread to read in async mode the messages sent by the server
			Thread readFromServerThread = new Thread(new Runnable() {
				
				private boolean isConnected = true;
				
				@Override
				public void run() {
					while (isConnected) {
						
						if (Thread.interrupted()) {
							System.out.println("Hasta Pronto!!!");
							isConnected = false;
						}
						
						try {
							DataInputStream input = new DataInputStream(socket.getInputStream());
						
							String message = input.readUTF();
							System.out.println(message);
						} catch (IOException e) {
							System.out.println("Error reading messages from server: " + e.getMessage());
							e.printStackTrace();
						}
					}
				}
				
				
			});
			
			readFromServerThread.setName("readFromServerThread");
			readFromServerThread.start();
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("nick: ");
			String nick = scanner.nextLine();
			
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			output.writeUTF(nick);
			
			System.out.println("Bienvenido " + nick + ". Ahora puedes escribir en el chat.");
			while (!"exit".equals(text)) {
				text = scanner.nextLine();
				
				output.writeUTF(nick + "|" + text); //send the text to the server
			}
			readFromServerThread.interrupt();
			scanner.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
