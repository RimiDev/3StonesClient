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
	
	public Board(String fileName)
	{
		resetBoard();
		
		File file = new File(fileName);

        try
        {
        	String line = "";
            Scanner scanner = new Scanner(file);
            
            int row = 1;
            
            while(scanner.hasNextLine())
            {
            	line = scanner.nextLine();
            	
            	String cols[] = line.split(",");
            	
             	for(int col = 0; col < cols.length; col++)
            	{
            		String stone = "";
            		
            		if("n".equals(cols[col]))
            			stone = "x";

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
	
	public void draw()
	{
		String[] yIndexes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
		
		ColumnFormatter<String> ageFormatter = ColumnFormatter.text(Alignment.CENTER, 3);

		Table.Builder builder = new Table.Builder("", yIndexes, ageFormatter);
		
		for(int i = 1; i <= 11; i++)
			builder.addColumn(""+i, columns.get(i), ageFormatter);

		Table table = builder.build();
		System.out.println(table);
	}
	
	public boolean isValidMove(int x, int y)
	{
		// If first 2 rows or last 2 rows
		if(y == 1 || y == 2 || y == 10 || y == 11)
			return false;
		
		// If first 2 columns or last 2 columns
		if(x == 1 || x == 2 || x == 10 || x == 11)
			return false;
		
		if((y == 3 || y == 9) && (x < 5 || x > 7))
			return false;
		
		if((x == 3 || x == 9) && (y == 4 || y == 8))
			return false;
		
		// If in middle
		if(x == 6 && y == 6)
			return false;
		
		return true;
	}
	
	public boolean isEmpty(int x, int y)
	{
		//TODO
		return false;
	}
}
