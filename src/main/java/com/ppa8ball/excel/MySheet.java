package com.ppa8ball.excel;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class MySheet implements Sheet
{
	private final XSSFSheet sheet;

	public MySheet(InputStream inputStream, int sheetNum)
	{
		sheet = GetWorkbookSheet(inputStream, sheetNum);
	}

	public PPACell getCell(int rowNum, int ColNum)
	{
		return new MyCell(sheet.getRow(rowNum).getCell(ColNum));
	}

	private static XSSFSheet GetWorkbookSheet(InputStream stream, int sheetNum)
	{
		XSSFWorkbook wb;
		try
		{
			wb = new XSSFWorkbook(stream);
			XSSFSheet sheet = wb.getSheetAt(sheetNum);
			wb.close();
			return sheet;
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
