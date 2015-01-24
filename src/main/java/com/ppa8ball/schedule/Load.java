package com.ppa8ball.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.ppa8ball.stats.CellHelp;

public class Load
{
	private final String PPAUrl = "http://www.ppa8ball.com";

	private final String FirstHalfSchedule = PPAUrl + "/schedule/2014-2015_First_Half_Schedule.xls";
	private final String SecondHalfSchedule = PPAUrl + "/schedule/2014-2015_Second_Half_Schedule.xls";

	private final int FirstHalfYear = 2014;
	private final int SecondHalfYear = 2015;

	public Schedule LoadFromExcel()
	{
		try
		{
			Workbook workbook = Workbook.getWorkbook(getExcelSpreadSheet(FirstHalfSchedule));

			Sheet sheet = workbook.getSheet(0);

			Schedule firstHalf = getSchedule(sheet, FirstHalfYear);

			workbook = Workbook.getWorkbook(getExcelSpreadSheet(SecondHalfSchedule));

			sheet = workbook.getSheet(0);

			Schedule secondHalf = getSchedule(sheet, SecondHalfYear);

			for (Week week : secondHalf.weeks)
			{
				firstHalf.weeks.add(week);
			}

			return firstHalf;

		} catch (BiffException e)
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

	private Schedule getSchedule(Sheet sheet, int year)
	{
		Cell startCell = sheet.getCell("B1");

		Cell cell = startCell;

		Schedule schedule = new Schedule();

		while (schedule.weeks.size() < 9)
		{
			Week week = getWeek(sheet, cell);

			if (week != null)
			{

				// Match match = null;

				while (week.getMatches().size() < 5)
				{
					cell = CellHelp.getCellBelow(sheet, cell);

					Match match = getMatch(cell);

					if (match != null)
					{
						match.week = week.number;
						match.season = week.season;
						match.match = week.getMatches().size();
						week.getMatches().add(match);
						
						cell = CellHelp.getCellBelow(sheet, cell);
						
						String [] tables = getTables(cell);
						
						match.table1 = tables[0];
						match.table2 = tables[1];
						
						
					}
				}

				week.date.setYear(year - 1900);
				schedule.weeks.add(week);

			}
			cell = CellHelp.getNextTopColumn(sheet, cell);
		}

		return schedule;
	}

	private Match getMatch(Cell cell)
	{
		String contents = cell.getContents();

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

	private String [] getTables(Cell cell)
	{
		String contents = cell.getContents();

		if (contents.contains("Table"))
		{
			String [] tables = contents.substring("Tables ".length()).split("&");
			
			tables[0] = tables[0].trim();
			tables[1] = tables[1].trim();
			
			return tables;
		}
		return null;
	}

	private Date getDate(Cell cell)
	{
		String contents = cell.getContents();
		if (contents == null || !contents.isEmpty())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
			Date date = new Date();
			try
			{
				date = sdf.parse(contents);
				return date;
			} catch (ParseException e)
			{
			}
		}
		return null;
	}

	private int getWeekDate(Cell cell)
	{
		String contents = cell.getContents();

		if (contents == null || !contents.startsWith("Week"))
			return -1;

		String weekString = contents.substring(5);

		int week = Integer.parseInt(weekString);

		return week;
	}

	private Week getWeek(Sheet sheet, Cell cell)
	{
		Week week = null;
		Date date = null;

		for (int i = 0; i < 20; i++)
		{
			// look for a column with dates

			date = getDate(cell);

			if (date != null)
				break;

			cell = CellHelp.getCellBelow(sheet, cell);
		}

		if (date != null)
		{
			Cell weekCell = CellHelp.getCellBelow(sheet, cell);

			int weekNumber = getWeekDate(weekCell);

			if (weekNumber >= 0)
			{
				week = new Week("", weekNumber, date);
			}
		}
		return week;
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

}
