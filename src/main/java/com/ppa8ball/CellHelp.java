package com.ppa8ball;

import jxl.Cell;
import jxl.Sheet;

public class CellHelp {
	
	public static Cell getCellToRight(Sheet sheet, Cell cell)
	{
		return sheet.getCell(cell.getColumn()+1,cell.getRow());
	}
	
	public static Cell getCellBelow(Sheet sheet, Cell cell)
	{
		return sheet.getCell(cell.getColumn(),cell.getRow()+1);
	}

}
