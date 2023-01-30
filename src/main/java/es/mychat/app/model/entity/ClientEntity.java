package es.mychat.app.model.entity;

import java.io.DataOutputStream;
import java.net.Socket;

public class ClientEntity {

	private String id;
	
	private Socket socket;
	
	private DataOutputStream output;

	public ClientEntity(String id, Socket socket, DataOutputStream output) {
		this.id = id;
		this.socket = socket;
		this.output = output;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	public void setOutput(DataOutputStream output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "ClientEntity [id=" + id + ", socket=" + socket + ", output=" + output + "]";
	}
}
