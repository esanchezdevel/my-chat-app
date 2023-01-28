package es.mychat.app.queue;

import java.util.ArrayList;
import java.util.List;

public class MessagesQueue {

	private static final List<String> QUEUE = new ArrayList<>();
	
	public static void saveMessage(String message) {
		QUEUE.add(message);
	}
	
	public static String getLastMessage() {
		if (QUEUE.size() > 0) {
			String message = QUEUE.get(QUEUE.size() - 1);
			QUEUE.remove(QUEUE.size() - 1);
		
			return message;
		} else {
			return "";
		}
	}
}
