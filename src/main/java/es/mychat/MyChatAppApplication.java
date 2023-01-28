package es.mychat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.mychat.app.socket.SocketServer;

@SpringBootApplication
public class MyChatAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyChatAppApplication.class, args);
		
		SocketServer.init();
	}
}
