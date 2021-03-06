import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreeStonesClientInterface
{
	
	
	private Scanner keyboard = new Scanner(System.in);
	private Board board;
	
	public ThreeStonesClientInterface() throws IOException
	{
		board = new Board("./ThreeStonesBoard.csv");
		
		
		String startORquit = promptString("Type start to start the game and quit to quit the game", new String[]{"start", "quit"}, "Try again and please enter start or quit");
		
		if(startORquit.equalsIgnoreCase("start"))
		{
			System.out.println("...STARTING GAME...");
			
			board.draw();
		}
		else if(startORquit.equalsIgnoreCase("quit"))
		{
			System.out.println("...ENDING GAME...");
		}
	}
	
	/**
	 * Asks the user to enter an x and y values to be placed on the board.
	 * @return
	 * @throws IOException
	 */
	public int[] promptClientMove() throws IOException
	{
		int x = -1;
		int y = -1;
		int[] position = new int[] {x, y};
		while(!board.isValidMove(x, y))
		{
			System.out.println("Enter a valid position you wish to play.");
			
			x = promptInt("x: ", "Invalid x position. Please try again.", false);
			y = promptInt("y: ", "Invalid y position. Please try again.", false);
			
			position = new int[] {x, y};
			x = position[0];
			y = position[1];
		}
		
		return position;
	}
	
	public int[] promptForPosition()
	{
		System.out.println("Enter the position you wish to play.");
		
		int x = promptInt("x: ", "Invalid x position. Please try again.", false);
		
		int y = promptInt("y: ", "Invalid y position. Please try again.", false);
		
		int[] pos = new int[] {x, y};
		
		return pos;
	}
	
	public boolean isInteger(String value)
	{
		try
		{
			Integer.parseInt(value);
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
	}
	
	
	public String promptString(String message, String[] validValues, String invalidInputMessage) throws IOException 
	{
		System.out.println(message);
		
		String input = keyboard.nextLine();

		boolean valid = false;
	
		while(!valid)
		{	
			for(String value : validValues)
			{
				if(input.equalsIgnoreCase(value))
				{
					valid = true;
					break;
				}
			}
			
			if(!valid)
			{
				System.out.println(invalidInputMessage);
				input = keyboard.nextLine();
			}
		}
		
		return input;
	}
	
	public int promptInt(String message, String invalidInputMessage, boolean newLine)
	{
		if(newLine)
			System.out.println(message);
		else
			System.out.print(message);
		
		String input = keyboard.nextLine();
	
		while(!isInteger(input))
		{
			System.out.println(invalidInputMessage);
			input = keyboard.nextLine();
		}
		
		return Integer.parseInt(input);
	}
	
	public void setStone(int x, int y, String tile)
	{
		board.placeStone(x, y, tile);
		
		System.out.println("\n \n");
		
		board.draw();
	}
	
	public Board getBoard()
	{
		return board;
	}
	
}
