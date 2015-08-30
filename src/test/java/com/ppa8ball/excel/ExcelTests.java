package com.ppa8ball.excel;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;

public class ExcelTests
{
	
	@Test
	public void CanLoadExcelFile()
	{
		InputStream stream = getTestInputStream();
		assertTrue(stream != null);
		try
		{
			stream.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void CanLoadSheet()
	{
		Sheet sheet = getTestWorksheet();	
		assertNotNull(sheet);
	}
	
	@Test 
	public void CanGetNumericCell()
	{	
		Sheet sheet = getTestWorksheet();	
		
		PPACell cell = sheet.getCell(0,0);
		
		assertTrue(cell.IsNumeric());
		
		int v = cell.getIntValue();
		
		assertEquals(1, v);
	}
	@Test
	public void CanGetStringCell()
	{
		Sheet sheet = getTestWorksheet();	
		
		PPACell cell = sheet.getCell(0,1);
		
		assertTrue(cell.IsString());
		
		String v = cell.getStringValue();
		
		assertEquals("a", v);
	}
	@Test
	public void canGetDateCell()
	{
		Sheet sheet = getTestWorksheet();	
		
		PPACell cell = sheet.getCell(0,2);
		
		assertTrue(cell.IsDate());
		
		Date v = cell.getDateValue();
		//2001-09-15
		Date expectedDate = new Date(2001-1900,8,15);
		
		assertEquals(expectedDate, v);
	}
	
	@Test
	public void canGetCellBelow()
	{
		Sheet sheet = getTestWorksheet();	
		
		PPACell cell = sheet.getCell(0,0);
		
		PPACell cell2 = cell.getCellBelow();
		
		int v = cell2.getIntValue();
		
		assertEquals(2, v);
	}
	
	@Test
	public void canGetCellNextRow()
	{
		Sheet sheet = getTestWorksheet();	
		PPACell cell = sheet.getCell(0,0);
		
		PPACell cell2 = cell.getNextTopColumn();
		
		String v = cell2.getStringValue();
		
		assertEquals("a",v);
	}
	
	private Sheet getTestWorksheet()
	{
		return new MySheet(getTestInputStream(), 0);
	}
	
	private InputStream getTestInputStream()
	{
		return this.getClass().getResourceAsStream("ExcelTests.xlsx");
	}
}