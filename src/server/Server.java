package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {
	private final int serverPort;
	private ArrayList<ClientThread> workerList = new ArrayList<>();
	
	public Server(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public List<ClientThread> getWorkerList(){
		return workerList;
	}
	
	@Override 
	public void run() {
		   try {
	            ServerSocket serverSocket = new ServerSocket(serverPort);
	            while(true) {
	                System.out.println("About to accept client connection...");
	                Socket clientSocket = serverSocket.accept();
	                System.out.println("Accepted connection from " + clientSocket);
	                ClientThread worker = new ClientThread(this, clientSocket);
	                workerList.add(worker);
	                worker.start();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

}
