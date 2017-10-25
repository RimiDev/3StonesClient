import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppGUI
{
	
	//TRY
	java.io.BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	private Scanner keyboard = new Scanner(System.in);
	private Board board;
	
	public AppGUI() throws IOException
	{
		board = new Board("./ThreeStonesBoard.csv");
		
		
		String startORquit = promptString("Type start to start the game and quit to quit the game", new String[]{"start", "quit"}, "Try again and please enter start or quit");
		
		if(startORquit.equalsIgnoreCase("start"))
		{
			System.out.println("...STARTING GAME...");
			
			board.draw();
			
			while(true)
			{
				int[] position = getUserPosition();
				
				int x = position[0];
				int y = position[1] - 1;
				
				board.placeStone(x, y, "w");
				board.draw();
			}
		}
		else if(startORquit.equalsIgnoreCase("quit"))
		{
			System.out.println("...ENDING GAME...");
		}
	}
	
	private int[] getUserPosition() throws IOException
	{
		int[] position = promptForPosition();
		
		int x = position[0];
		int y = position[1];
		
		while(!board.isValidMove(x, y))
		{
			System.out.println("("+x+","+y+") is not a valid position. Please try again.");
			
			position = promptForPosition();
			x = position[0];
			y = position[1];
		}
		return position;
	}
	
	private int[] promptForPosition()
	{
		System.out.println("Enter the position you wish to play.");
		
		int x = promptInt("x: ", "Invalid x position. Please try again.", false);
		
		int y = promptInt("y: ", "Invalid y position. Please try again.", false);
		
		int[] pos = new int[] {x, y};
		
		return pos;
	}
	
	private boolean isInteger(String value)
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
	
	
	private String promptString(String message, String[] validValues, String invalidInputMessage) throws IOException 
	{
		System.out.println(message);
		
		String input = in.readLine();

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
	
	private int promptInt(String message, String invalidInputMessage, boolean newLine)
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
}
