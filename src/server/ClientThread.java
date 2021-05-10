package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;


public class ClientThread extends Thread{
	 private final Socket clientSocket;
	 private String login = null;
	 private final Server server;
	 private OutputStream outputStream;
	 
	public ClientThread(Server server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
		
	}
	
	


	@Override
	public void run() {
		try {
			handleClientSocket();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void handleClientSocket() throws IOException, InterruptedException {
		InputStream inputStream = clientSocket.getInputStream();
		this.outputStream = clientSocket.getOutputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line;
		while((line = reader.readLine()) !=null) {
			String[] tokens = line.split(" ");
			if(tokens != null && tokens.length>0) {
				String command = tokens[0];
				if("quit".equalsIgnoreCase(line)) {
					break;
				}
				else if("login".equalsIgnoreCase(command)) {
					handleLogin(outputStream,tokens);
				}
				else if("msg".equalsIgnoreCase(command)) {
					handleMessage(tokens);
				}
				else {
					String msg = "unknown command: " + command + "\n";
					outputStream.write(msg.getBytes());
				}
			}
			
			String msg = "You typed: " + line + "\n";
			outputStream.write(msg.getBytes());
		}
		clientSocket.close();
	}
	
	public String getLogin() {
		return login;
				}
	private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
		if(tokens.length ==3) { //if there are three word commands
			String login = tokens[1]; //each word is split into a token with its values
			String password = tokens[2];
			if((login.equals("austin") && password.equals("password")) || (login.equals("brad")&& password.equals("password"))) { // command would be login <user> <password>
				String msg = "Login sucessfull\n";
				outputStream.write(msg.getBytes());
				this.login = login;
				System.out.println("User logged in sucessfully: "+ login);
				
				String onlineMsg =  login + " is now online\n";
				List<ClientThread> workerList = server.getWorkerList();
				for(ClientThread worker : workerList) {
					worker.send(onlineMsg);
				}
			}
			else {
				String msg = "Wrong username or password\n";
				outputStream.write(msg.getBytes());
			}
		}
	}
	
	private void handleMessage(String[] tokens) throws IOException {
		String sendTo = tokens[1];
		String body = tokens[2]; 
		
		List<ClientThread> workerList = server.getWorkerList();
		for(ClientThread worker : workerList) {
			if(sendTo.equalsIgnoreCase(worker.getLogin())) {
				String outMsg = "You've recieved a message from: " + login + ": " + body + "\n";
				worker.send(outMsg);
			}
		}
	}
	
	private void send(String msg) throws IOException{
		outputStream.write(msg.getBytes());
	}


}
