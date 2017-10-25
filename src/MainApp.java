import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



public class MainApp
{	
	
	static boolean isValid = false;
	
	public static void main(String[] args)
	{					
		//Scanner object
		Scanner clientInput = new Scanner(System.in);
		
		String serverAddress;
		String port = "";
		
		System.out.println("Please enter an IP address: ");
		serverAddress = clientInput.nextLine();
		
		while (!isValid) {
			System.out.println("Please enter a PORT number: ");
			port = clientInput.nextLine();
			isValid = inputValidation(port);
		} 
		
		clientInput.close();
		
		
		//Establishing a connection.
		try (Socket clientSocket = new Socket(serverAddress, Integer.valueOf(port))){
			
			InputStream in =  clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			byte[] respondPacket;
			byte[] byteRecv;
			
			
			respondPacket = new byte[1];
			respondPacket[0] = (byte) 0;
			out.write(respondPacket);
			
			byteRecv = new byte[7];
			in.read(byteRecv);
			
			System.out.println("Byte recieved: " + byteRecv[0]); 
			
			switch (byteRecv[0]) {
				case 0:
//					respondPacket = new byte[3];
//					respondPacket[0] = (byte) 1;
//					respondPacket[1] = (byte) 5;
//					respondPacket[2] = (byte) 6;
					
					respondPacket = new byte[1];
					respondPacket[0] = (byte) 9;
					
					
					out.write(respondPacket);
					
					
					
					//AppGUI gui = new AppGUI();
					
					
				case 9:
					
					
					
					respondPacket = new byte[1];
					respondPacket[0] = (byte) 9;
					
					out.write(respondPacket);
					
					System.out.println("Hey babe");
					
					
					
					
					
			} // switch close
			
			
			
			
			
			
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	} // end of main
	
	
	//Validation for the port number, to check if it is a numeric input.
	public static boolean inputValidation(String port) {
		
		  return port.matches("[0-9]+");
		
	}
	
	
	
} // end of class
