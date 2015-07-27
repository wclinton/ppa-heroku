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
		InputStream stream = this.getClass().getResourceAsStream("ExcelTests.xlsx");
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
		InputStream stream = this.getClass().getResourceAsStream("ExcelTests.xlsx");
		assertNotNull(stream);
		
		Sheet sheet = new MySheet(stream, 0);
		
		assertNotNull(sheet);
	}
	
	@Test 
	public void CanGetNumericCell()
	{
		InputStream stream = this.getClass().getResourceAsStream("ExcelTests.xlsx");
		assertNotNull(stream);
		
		Sheet sheet = new MySheet(stream, 0);
		
		PPACell cell = sheet.getCell(0,0);
		
		assertTrue(cell.IsNumeric());
		
		int v = cell.getIntValue();
		
		assertEquals(1, v);
	}
	@Test
	public void CanGetStringCell()
	{
		InputStream stream = this.getClass().getResourceAsStream("ExcelTests.xlsx");
		assertNotNull(stream);
		
		Sheet sheet = new MySheet(stream, 0);
		
		PPACell cell = sheet.getCell(0,1);
		
		assertTrue(cell.IsString());
		
		String v = cell.getStringValue();
		
		assertEquals("a", v);
	}
	@Test
	public void canGetDateCell()
	{
		InputStream stream = this.getClass().getResourceAsStream("ExcelTests.xlsx");
		assertNotNull(stream);
		
		Sheet sheet = new MySheet(stream, 0);
		
		PPACell cell = sheet.getCell(0,2);
		
		assertTrue(cell.IsDate());
		
		Date v = cell.getDateValue();
		//2001-09-15
		Date expectedDate = new Date(2001-1900,8,15);
		
		assertEquals(expectedDate, v);
	}
}
