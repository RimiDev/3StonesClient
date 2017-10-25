import java.io.IOException;
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
			
			AppGUI gui = new AppGUI();
			
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
