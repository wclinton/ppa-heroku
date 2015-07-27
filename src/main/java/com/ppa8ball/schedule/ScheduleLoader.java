package com.ppa8ball.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.Iterator;







import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.appengine.repackaged.org.joda.time.DateTime;
import com.ppa8ball.excel.MySheet;
import com.ppa8ball.excel.PPACell;
import com.ppa8ball.excel.Sheet;
import com.ppa8ball.stats.CellHelp;

public class ScheduleLoader implements DataLoader
{

	private final String PPAUrl = "http://www.ppa8ball.com";

	private final String FirstHalfSchedule = PPAUrl + "/schedule/2014-2015_First_Half_Schedule.xls";
	private final String SecondHalfSchedule = PPAUrl + "/schedule/2014-2015_Second_Half_Schedule.xls";

	@Override
	public Schedule Load()
	{
		Schedule firstHalf = Load(FirstHalfSchedule);
		Schedule secondHalf = Load(SecondHalfSchedule);
		
		firstHalf.weeks.addAll(secondHalf.weeks);
		
		return firstHalf;
	}

	private Schedule Load(String url)
	{
		Sheet sheet;
		sheet = new MySheet(getExcelSpreadSheet(url),0);
		return ProcessWorkbookSheetToSchedule(sheet);
	}
	
	
	private XSSFSheet GetWorkbookSheet(InputStream stream) throws IOException
	{
		XSSFWorkbook wb = new XSSFWorkbook(stream);
		XSSFSheet sheet = wb.getSheetAt(0);
		return sheet;
	}
	
	
	private Schedule ProcessWorkbookSheetToSchedule(Sheet sheet)
	{
		
		//Cell startCell = sheet.getCell("B1");
		
		//XSSFRow startRow = sheet.getRow(0);
		
		PPACell cell = sheet.getCell(0,2);

		Schedule schedule = new Schedule();

		while (schedule.weeks.size() < 9)
		{
			Week week = getWeek(cell);

			if (week != null)
			{

				// Match match = null;

				while (week.getMatches().size() < 5)
				{
					cell = cell.getCellBelow();

					Match match = getMatch(cell);

					if (match != null)
					{
						//match.week = week.number;
						//match.season = week.season;
						match.match = week.getMatches().size();
						week.getMatches().add(match);
						
						cell = cell.getCellBelow();
						
						String [] tables = getTables(cell);
						
						match.table1 = tables[0];
						match.table2 = tables[1];
						
						
					}
				}

				week.date.setYear(DateTime.now().getYear() - 1900);
				schedule.weeks.add(week);

			}
			cell = cell.getNextTopColumn();
		}

		return schedule;
	}
	
	private Week getWeek(PPACell cell)
	{
		Week week = null;
		java.util.Date date= null;

		for (int i = 0; i < 20; i++)
		{
			// look for a row with dates
			
			if (cell.IsDate())
			{
				date = cell.getDateValue();
			}

			if (date != null)
				break;

			cell = cell.getCellBelow();
		}

		if (date != null)
		{
			PPACell weekCell = cell.getCellBelow();

			int weekNumber = getWeekDate(weekCell);
			
			

			if (weekNumber >= 0)
			{
				week = new Week("", weekNumber, new java.sql.Date(date.getTime()));
			}
		}
		return week;
	}

	public void readXLSXFile() throws IOException
	{
		InputStream ExcelFileToRead = getExcelSpreadSheet(FirstHalfSchedule);

		XSSFWorkbook myWorkBook = new XSSFWorkbook(ExcelFileToRead);
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

		XSSFWorkbook test = new XSSFWorkbook();

		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row;
		XSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row = (XSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			while (cells.hasNext())
			{
				cell = (XSSFCell) cells.next();

				if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue() + " ");
				} else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue() + " ");
				} else
				{
					// U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}

	}

	private InputStream getExcelSpreadSheet(String url)
	{
		try
		{
			return new URL(url).openStream();
		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	private XSSFCell getCellBelow(XSSFCell cell )
	{
		XSSFSheet sheet = cell.getSheet();
		
		int currentRowNum = cell.getRowIndex();
		int currentColNum = cell.getColumnIndex();
		
		return sheet.getRow(currentRowNum +1).getCell(currentColNum);
	}
	
	private int getWeekDate(PPACell cell)
	{
		String contents = cell.getStringValue();

		if (contents == null || !contents.startsWith("Week"))
			return -1;

		String weekString = contents.substring(5);

		int week = Integer.parseInt(weekString);

		return week;
	}
	
	private Match getMatch(PPACell cell)
	{
		String contents = cell.getStringValue();

		if (contents.contains("at"))
		{
			Match match = new Match();
			String[] teams = contents.split("at");

			match.homeTeam = Integer.parseInt(teams[1].trim());
			match.awayTeam = Integer.parseInt(teams[0].trim());

			return match;
		}

		return null;
	}
	
	private String [] getTables(PPACell cell)
	{
		String contents = cell.getStringValue();

		if (contents.contains("Table"))
		{
			String [] tables = contents.substring("Tables ".length()).split("&");
			
			tables[0] = tables[0].trim();
			tables[1] = tables[1].trim();
			
			return tables;
		}
		return null;
	}
}