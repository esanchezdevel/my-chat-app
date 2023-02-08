package es.mychat.app.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
							System.out.println("Good bye!!!");
							isConnected = false;
							return;
						}
						
						try {
							BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							
							String message = input.readLine();
							System.out.println(message);
						} catch (IOException e) {
							System.out.println("Connection with server closed");
						}
					}
				}
			});
			
			readFromServerThread.setName("readFromServerThread");
			readFromServerThread.start();
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("nick: ");
			String nick = scanner.nextLine();
			
			PrintWriter output = new PrintWriter(socket.getOutputStream());
            output.write(nick + "\n");
            output.flush();
			
			System.out.println("Welcome " + nick + ". Now you can write in the chat.");
			while (!"exit".equals(text)) {
				text = scanner.nextLine();
				
				output.write(nick + "|" + text + "\n");
				output.flush();
			}
			readFromServerThread.interrupt();
			scanner.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
