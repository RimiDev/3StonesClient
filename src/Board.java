import java.util.ArrayList;
import java.util.List;

import io.bretty.console.table.Alignment;
import io.bretty.console.table.ColumnFormatter;
import io.bretty.console.table.Table;

public class Board
{
	private List<String[]> columns = new ArrayList<String[]>();
	
	public Board()
	{
		resetBoard();
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
	
	public boolean isEmpty(int x, int y)
	{
		//TODO
		return false;
	}
}
