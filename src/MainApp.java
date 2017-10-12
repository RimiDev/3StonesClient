import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;

public class MainApp {

	
	public static void main(String[] args)
	{

		
		
		
		
		

		


		
		new TmpDrawingClass();
	}
	

}
class TmpDrawingClass
{
	private List<String[]> columns = new ArrayList<String[]>();
	private Scanner keyboard = new Scanner(System.in);
	
	public TmpDrawingClass()
	{
		String startORquit = promptString("Type start to start the game and quit to quit the game", new String[]{"start", "quit"}, "Try again and please enter start or quit");
		
		if(startORquit.equalsIgnoreCase("start"))
		{
			System.out.println("...STARTING GAME...");
			
			for(int i = 0; i < 12; i++)
				columns.add(new String[11]);
			
			for(int col = 0; col < columns.size(); col++)
			{
				String[] column = columns.get(col);
						
				for(int row = 0; row < column.length; row++)
				{
					placeStone(col, row, "");
				}
			}

			drawBoard();
			
			int[] position = getUserPosition();
			
			placeStone(position[1]-1, position[0]-1, "x");
			
			drawBoard();
			
			System.out.println("POSITION x: " + position[0] + " y: " + position[1]);
		}
		else if(startORquit.equalsIgnoreCase("quit"))
		{
			System.out.println("...ENDING GAME...");
		}
	}
	
	private void placeStone(int x, int y, String tmpPlayerType)
	{
		columns.get(x)[y] = tmpPlayerType;
	}
	
	private void drawBoard()
	{
		String[] yIndexes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
		
		ColumnFormatter<String> ageFormatter = ColumnFormatter.text(Alignment.CENTER, 3);

		Table.Builder builder = new Table.Builder("", yIndexes, ageFormatter);
		
		for(int i = 1; i <= 11; i++)
			builder.addColumn(""+i, columns.get(i), ageFormatter);

		Table table = builder.build();
		System.out.println(table);
	}
	
	private int[] getUserPosition()
	{
		int[] position = promptForPosition();
		
		int x = position[0];
		int y = position[1];
		
		String yesORno = promptString("You chose ["+ x + "," + y + "], correct (yes or no)?", new String[]{"yes", "no"}, "Try again and please enter yes or no.");
		
		while(yesORno.equalsIgnoreCase("no"))
		{
			println("...CORRECTING PREVIOUS CHOICE...");
			
			position = promptForPosition();
			x = position[0];
			y = position[1];
			
			yesORno = promptString("You chose ["+ x + "," + y + "], correct (yes or no)?", new String[]{"yes", "no"}, "Try again and please enter yes or no.");
		}
		
		return position;
	}
	
	private int[] promptForPosition()
	{
		println("Enter the position you wish to play.");
		
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
	
	private void print(String message)
	{
		System.out.print(message);
	}
	
	private void println(String message)
	{
		System.out.println(message);
	}
	
	private String promptString(String message, String[] validValues, String invalidInputMessage)
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
