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
		
		while (!isValid)
		{
			System.out.println("Please enter a PORT number: ");
			port = clientInput.nextLine();
			isValid = inputValidation(port);
		} 

		//Establishing a connection.
		try (Socket clientSocket = new Socket(serverAddress, Integer.valueOf(port))){
			
			InputStream in =  clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			byte[] sendPacket = null;
			byte[] receivePacket;
			
			
			sendPacket = new byte[1];
			sendPacket[0] = (byte) 0;
			out.write(sendPacket);
			
			receivePacket = new byte[7];
			in.read(receivePacket);
			
			System.out.println("Byte received: " + receivePacket[0]); 
			
			switch (receivePacket[0]) 
			{
				case 0:
					AppGUI gui = new AppGUI();
					break;
				
			} // switch close
		} 
		catch (NumberFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		clientInput.close();
	} // end of main
	
	
	//Validation for the port number, to check if it is a numeric input.
	public static boolean inputValidation(String port) 
	{	
		return port.matches("[0-9]+");
	}
	
	
	
} // end of class
