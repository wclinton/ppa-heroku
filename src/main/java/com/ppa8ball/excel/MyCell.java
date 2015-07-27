package com.ppa8ball.excel;
import java.sql.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;


public class MyCell implements PPACell
{

	private XSSFCell cell;
	
	public MyCell(XSSFCell xssfCell)
	{
		this.cell = xssfCell;
	}

	@Override
	public boolean IsDate()
	{
		return HSSFDateUtil.isCellDateFormatted(cell);
	}

	@Override
	public boolean IsString()
	{
		if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
			return true;
		return false;
			
	}

	

	@Override
	public boolean IsNumeric()
	{
		if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
			return true;
		return false;
	}

	@Override
	public Date getDateValue()
	{
		if (IsDate())
		{
			return new Date(cell.getDateCellValue().getTime());
		}
		return null;
	}

	@Override
	public String getStringValue()
	{
		if (IsString())
		{
			return cell.getStringCellValue();
		}
		return null;
	}

	@Override
	public PPACell getCellBelow()
	{
		int rowNum = cell.getRowIndex();
		int cellNum = cell.getColumnIndex();
		
		XSSFSheet sheet = cell.getSheet();
		
		XSSFCell newCell = sheet.getRow(rowNum +1).getCell(cellNum);
		
		return new MyCell(newCell);
	}

	@Override
	public PPACell getNextTopColumn()
	{
		int column = getColumnIndex() +1;
		return new MyCell(cell.getSheet().getRow(0).getCell(column));	
	}

	@Override
	public int getRowIndex()
	{
		return cell.getRowIndex();
	}

	@Override
	public int getColumnIndex()
	{
		return cell.getColumnIndex();
	}

	@Override
	public int getIntValue()
	{
		return (int)cell.getNumericCellValue();
	}
}
