import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



public class MainApp
{	
	
	static boolean isValid = false;
	static boolean gameOver = false;
	static boolean firstEntry = true;
	
	
	public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException
	{			
		int[] userPos = null;
		Board board = null;
		AppGUI gui = null;
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

		Socket clientSocket = new Socket(serverAddress, Integer.valueOf(port));
	while (!gameOver){
		//Establishing a connection.
		try {			
			
			InputStream in =  clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			byte[] sendPacket = null;
			byte[] receivePacket;
			
			
			if (firstEntry) {
			sendPacket = new byte[1];
			sendPacket[0] = (byte) 0;
			out.write(sendPacket);
			firstEntry = false;
			} 
			
			
			receivePacket = new byte[8];
			in.read(receivePacket);
			System.out.println("Byte received: " + receivePacket[0]); 
			
			switch (receivePacket[0]) 
			{
				case 0:
					gui = new AppGUI();
					userPos = gui.getUserPosition();
					
					sendPacket = new byte[3];
					sendPacket[0] = (byte) 1;
					sendPacket[1] = (byte) (userPos[0]);
					sendPacket[2] = (byte) (userPos[1]);
					
					out.write(sendPacket);
					
					System.out.println("user pos" + userPos[0] + "  ||  " +  userPos[1]);
					break;
				case 1:
					
					int clientX = receivePacket[1];
					int clientY = receivePacket[2];
					int serverX = receivePacket[3];
					int serverY = receivePacket[4];
					int serverScore = receivePacket[5];
					int clientScore = receivePacket[6];
					String gameOver = String.valueOf(receivePacket[7]);
					gui.setStone(clientX, clientY, "w");
					gui.setStone(serverX, serverY, "b");
					sendPacket = new byte[1];
					sendPacket[0] = (byte)1;
					
					userPos = gui.getUserPosition();
					
					sendPacket = new byte[3];
					sendPacket[0] = (byte) 1;
					sendPacket[1] = (byte) (userPos[0]);
					sendPacket[2] = (byte) (userPos[1]);
					
					out.write(sendPacket);
					
					System.out.println("user pos" + userPos[0] + "  ||  " +  userPos[1]);
					
					System.out.println("cx: " + clientX + " cy: " + clientY + " sX: " + serverX + " sY: " + serverY + " ss: " + serverScore + " cs: " + clientScore + " GO: " + gameOver);
					break;
					
				case 2:
					System.out.println("Invalid stone placement, wtf you do ? ? ? ? ?");
					
					userPos = gui.getUserPosition();
					
					sendPacket = new byte[3];
					sendPacket[0] = (byte) 1;
					sendPacket[1] = (byte) (userPos[0]);
					sendPacket[2] = (byte) (userPos[1]);
					
					out.write(sendPacket);
					
					
			} // switch close
			
		} // try
		
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
		
	} // end of gameOver loop

		
		
	} // end of main
	
	
	//Validation for the port number, to check if it is a numeric input.
	public static boolean inputValidation(String port) 
	{	
		return port.matches("[0-9]+");
	}
	
	
	
} // end of class
