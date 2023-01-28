package es.mychat.app.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mychat.app.queue.MessagesQueue;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@PostMapping("/message/send")
	public ResponseEntity<?> messageSend(@RequestBody Map<String, String> params) {
		
		System.out.println("message send received: " + params);
		
		String message = params.get("value");
		
		MessagesQueue.saveMessage(message);
		
		return ResponseEntity.ok(new ResponseBean("success"));
	}

	private class ResponseBean {
		private String code;
		
		public ResponseBean(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return this.code;
		}
	}
}
