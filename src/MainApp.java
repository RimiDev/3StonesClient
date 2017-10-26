import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainApp
{
	public static void main(String[] args) throws IOException
	{
		// Indicators that check if the entered port number is valid; 
		// if the Three Stones game is over;
		// if the user has just started the game and is about to make the first move.
		boolean validPortNumber = false;
		boolean gameOver = false;
		boolean firstEntry = true;
		
		// Holds the user's current desired X,Y position on the Three Stones game board to set a stone on.
		int[] userMove = null;
		
		// Scanner that will be used for user input
		Scanner clientInput = new Scanner(System.in);
		
		// Controls the user interface of the client
		ThreeStonesClientInterface threeStonesGame = null;

		// Variables that hold the IP address of the server as well as the port being used
		String serverAddress;
		String port = "";

		// Prompts the user to enter the server's IP address
		System.out.println("Please enter an IP address: ");
		serverAddress = clientInput.nextLine();

		// Prompts the user to enter the port number that the server is using.
		// Will keep prompting the user until the user enters a numeric value that can be parsed into a int.
		while (!validPortNumber)
		{
			System.out.println("Please enter port number: ");
			port = clientInput.nextLine();
			validPortNumber = inputValidation(port);
		}
		
		// Creating a Socket with the server's IP address and port number.
		Socket clientSocket = new Socket(serverAddress, Integer.valueOf(port));
		
		// While the game is not over, keep playing.
		while (!gameOver)
		{
			// Creating InputStream and OutputStream
			InputStream in =  clientSocket.getInputStream();
			OutputStream out = clientSocket.getOutputStream();
			
			// byte arrays that will be used as send and receive packets
			byte[] sendPacket = null;
			byte[] receivePacket;

			if (firstEntry) 
			{
				sendPacket = new byte[1];
				sendPacket[0] = (byte) 0;
				out.write(sendPacket);
				firstEntry = false;
			}
			
			receivePacket = new byte[8];
			in.read(receivePacket);
			System.out.println("Byte received from server: " + receivePacket[0]);

			switch (receivePacket[0])
			{
				// If the first byte is a 0, then the server has acknowledged the request for a game.
				// The client will then send back a packet to the server with his X,Y move.
				// The server will then make sure that the place the client wants to place a stone on is empty.
				case 0:
					threeStonesGame = new ThreeStonesClientInterface();
					
					// Make a move and send to server
					userMove = threeStonesGame.promptClientMove();
					sendPacket = new byte[3];
					sendPacket[0] = (byte) 1;
					sendPacket[1] = (byte) (userMove[0]);
					sendPacket[2] = (byte) (userMove[1]);
					out.write(sendPacket);

					System.out.println("Sending to server user's move -->  x:" + userMove[0] + " , y:" +  userMove[1]);
					break;
					
				// Receives the current state of the game from the server, then prompts the user to make a new move
				case 1:
					// Packet that the client is receiving includes the positions of the client's and server's moves,
					// the scores of the client and server, as well as a char that tells the client whether or not the game is over.
					int clientPositionX = receivePacket[1];
					int clientPositionY = receivePacket[2];
					int serverPositionX = receivePacket[3];
					int serverPositionY = receivePacket[4];
					int clientScore = receivePacket[5];
					int serverScore = receivePacket[6];
					char gameStatus = (char)receivePacket[7];
							
					threeStonesGame.getBoard().removeLastMove();
					
					// Sets the moves of the client and server onto the client's board, according to the positions of the receive packet.
					// "w" represents white stone, "b" represents black stone.
					threeStonesGame.setStone(clientPositionX, clientPositionY, "W");
					threeStonesGame.setStone(serverPositionX, serverPositionY, "[B]");
					
					threeStonesGame.getBoard().setNewMove(serverPositionX, serverPositionY);
	
					System.out.println("Client's score: " + clientScore + "\tServer's score: " + serverScore);
					
					if(gameStatus == 'B')
					{
						gameOver = true;
					}
					else if(gameStatus == 'A')
					{
						// Make a new move and send to server
						userMove = threeStonesGame.promptClientMove();
						sendPacket = new byte[3];
						sendPacket[0] = (byte) 1;
						sendPacket[1] = (byte) (userMove[0]);
						sendPacket[2] = (byte) (userMove[1]);
						out.write(sendPacket);
	
						System.out.println("Sending to server user's move -->  x:" + userMove[0] + " , y:" +  userMove[1]);
					}
					break;
					
				// Let's the client know that the place he wants to set a stone on is invalid.
				case 2:
					System.out.println("Can't place a stone on selected area. Try again.");

					userMove = threeStonesGame.promptClientMove();

					// Make a new move and send to server
					userMove = threeStonesGame.promptClientMove();
					sendPacket = new byte[3];
					sendPacket[0] = (byte) 1;
					sendPacket[1] = (byte) (userMove[0]);
					sendPacket[2] = (byte) (userMove[1]);
					out.write(sendPacket);
			} // end of switch statement
			
			// Once the game is over, asks the user if he wants to play again
			if(gameOver == true)
			{
				String playAgain = threeStonesGame.promptString("Game is now over, want to play again?", new String[] {"yes", "no"}, "Please enter yes or no");

				if(playAgain.equals("yes"))
				{
					gameOver = false;
					firstEntry = true;
					// Sends a 3 - Sends a packet to the server saying if user wants to play again
					sendPacket = new byte[2];
					sendPacket[0] = (byte) 3;
					sendPacket[1] = (byte) 'A';
					out.write(sendPacket);
					System.out.println("Telling server to play again...");
				}
				else if(playAgain.equals("no"))
				{
					// Sends a 3 - Sends a packet to the server saying if user wants to play again
					sendPacket = new byte[2];
					sendPacket[0] = (byte) 3;
					sendPacket[1] = (byte) 'B';
					out.write(sendPacket);
					System.out.println("Exiting the game...");
				}
			}
		} // end of while loop
		
		// Closing both the Scanner and the Socket
		clientInput.close();
		clientSocket.close();
		
	} // end of main method

	//Validation for the port number, to check if it is a numeric input.
	public static boolean inputValidation(String port)
	{
		return port.matches("[0-9]+");
	}

} // end of class
