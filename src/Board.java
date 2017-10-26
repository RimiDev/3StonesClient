import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;

public class Board
{
	private List<String[]> columns = new ArrayList<String[]>();
	
	private int lastPlayX = -1;
	private int lastPlayY = -1;
	
	public Board(String fileName)
	{
		resetBoard();
		
		File file = new File(fileName);

        try
        {
        	String line = "";
            Scanner scanner = new Scanner(file);
            
            //IF THERES A BUG LOOK HERE
            int row = 0;
            
            while(scanner.hasNextLine())
            {
            	line = scanner.nextLine();
            	
            	String cols[] = line.split(",");
            	
             	for(int col = 0; col < cols.length; col++)
            	{
            		String stone = "";
            		
            		if("e".equals(cols[col]))
            			stone = "-";

            		placeStone(row, col, stone);
            	}
 
            	row++;
            }
            
            scanner.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
	}
	
	public void resetBoard()
	{
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
	}
	
	public void placeStone(int x, int y, String tmpPlayerType)
	{
		columns.get(x)[y] = tmpPlayerType;
	}
	
	public void removeLastMove()
	{
		if(lastPlayX == -1 && lastPlayY == -1)
			return;

		columns.get(lastPlayX)[lastPlayY] = "B";
	}
	
	public void setNewMove(int moveX, int moveY)
	{
		lastPlayX = moveX;
		lastPlayY = moveY;
	}
	
	public void draw()
	{
		String[] yIndexes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		
		ColumnFormatter<String> ageFormatter = ColumnFormatter.text(Alignment.CENTER, 3);

		Table.Builder builder = new Table.Builder("", yIndexes, ageFormatter);
		
		for(int i = 0; i < 11; i++)
			builder.addColumn(""+i, columns.get(i), ageFormatter);

		Table table = builder.build();
		System.out.println(table);
	}
	
	public boolean isValidMove(int x, int y)
	{
		// If outside of range
		if(x < 0 || x > 10 || y < 0 || y > 10)
			return false;
		
		// If first 2 rows or last 2 rows
		if(y == 0 || y == 1 || y == 9 || y == 10)
			return false;
		
		// If first 2 columns or last 2 columns
		if(x == 0 || x == 1 || x == 9 || x == 10)
			return false;
		
		if((y == 2 || y == 8) && (x < 4 || x > 6))
			return false;
		
		if((x == 2 || x == 8) && (y == 3 || y == 7))
			return false;
		
		// If in middle
		if(x == 5 && y == 5)
			return false;
		
		return true;
	}
}
