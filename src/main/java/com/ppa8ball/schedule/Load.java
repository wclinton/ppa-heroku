package com.ppa8ball.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.ppa8ball.models.Match;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.stats.CellHelp;
import com.ppa8ball.service.TeamServiceImpl;

public class Load
{
	private final String PPAUrl = "http://www.ppa8ball.com";
	private final int FirstHalfYear = 2014;
	private final int SecondHalfYear = 2015;
	private final TeamService teamService;
	private final Season currentSeason;

	public Load(Session session, Season season)
	{
		teamService = new TeamServiceImpl(session);
		currentSeason = new SeasonServiceImpl(session).GetCurrent();
	}

	public List<Week> LoadFromExcel()
	{
		try
		{
			Workbook workbook = Workbook.getWorkbook(getExcelSpreadSheet(getFirstHalfScheduleUrl(currentSeason)));

			Sheet sheet = workbook.getSheet(0);

			List<Week> firstHalfWeeks = getWeeks(sheet, FirstHalfYear);

			workbook = Workbook.getWorkbook(getExcelSpreadSheet(getSecondHalfScheduleUrl(currentSeason)));

			sheet = workbook.getSheet(0);

			List<Week> SecondHalfWeeks = getWeeks(sheet, SecondHalfYear);

			firstHalfWeeks.addAll(SecondHalfWeeks);

			return firstHalfWeeks;

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

	private String getSecondHalfScheduleUrl(Season season)
	{
		return PPAUrl + "/schedule/" + season.getDescription() + "_Second_Half_Schedule.xls";
	}

	private String getFirstHalfScheduleUrl(Season season)
	{
		return PPAUrl + "/schedule/" + season.getDescription() + "_First_Half_Schedule.xls";
	}

	private List<Week> getWeeks(Sheet sheet, int year)
	{
		Cell startCell = sheet.getCell("B1");
		Cell cell = startCell;

		List<Week> weeks = new ArrayList<>();

		while (weeks.size() < 9)
		{
			Week week = getWeek(sheet, cell, year);

			if (week != null)
			{
				while (week.getMatches().size() < 5)
				{
					cell = CellHelp.getCellBelow(sheet, cell);
					Match match = getMatch(week.getMatches().size() + 1, sheet, cell);

					if (match != null)
					{
						match.setWeek(week);
						week.getMatches().add(match);
					}
				}
				weeks.add(week);
			}
			cell = CellHelp.getNextTopColumn(sheet, cell);
		}

		return weeks;

	}

	// private Schedule getSchedule(Sheet sheet, int year)
	// {
	// Cell startCell = sheet.getCell("B1");
	//
	// Cell cell = startCell;
	//
	// Schedule schedule = new Schedule();
	//
	// while (schedule.weeks.size() < 9)
	// {
	// Week week = getWeek(sheet, cell);
	//
	// if (week != null)
	// {
	//
	// // Match match = null;
	//
	// while (week.getMatches().size() < 5)
	// {
	// cell = CellHelp.getCellBelow(sheet, cell);
	//
	// Match match = getMatch(cell);
	//
	// if (match != null)
	// {
	// // match.week = week.number;
	// // match.season = week.season;
	// match.match = week.getMatches().size();
	// week.getMatches().add(match);
	//
	// cell = CellHelp.getCellBelow(sheet, cell);
	//
	// String[] tables = getTables(cell);
	//
	// match.table1 = tables[0];
	// match.table2 = tables[1];
	//
	// }
	// }
	//
	// week.date.setYear(year - 1900);
	// schedule.weeks.add(week);
	//
	// }
	// cell = CellHelp.getNextTopColumn(sheet, cell);
	// }
	//
	// return schedule;
	// }

	private Match getMatch(int number, Sheet sheet, Cell cell)
	{
		String contents = cell.getContents();

		if (contents.contains("at"))
		{
			String[] teams = contents.split("at");

			int homeTeamNumber = Integer.parseInt(teams[1].trim());
			int awayTeamNumber = Integer.parseInt(teams[0].trim());

			Team home = teamService.GetByNumber(currentSeason, homeTeamNumber);
			Team away = teamService.GetByNumber(currentSeason, awayTeamNumber);

			cell = CellHelp.getCellBelow(sheet, cell);

			String[] tables = getTables(cell);

			return new Match(number, tables[0], tables[1], home, away);
		}
		return null;
	}

	private String[] getTables(Cell cell)
	{
		String contents = cell.getContents();

		if (contents.contains("Table"))
		{
			String[] tables = contents.substring("Tables ".length()).split("&");

			tables[0] = tables[0].trim();
			tables[1] = tables[1].trim();

			return tables;
		}
		return null;
	}

	private Date getDate(Cell cell, int year)
	{
		String contents = cell.getContents();
		if (contents == null || !contents.isEmpty())
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");

			try
			{
				java.util.Date utilDate = sdf.parse(contents);
				utilDate.setYear(year - 1900);

				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				return sqlDate;
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

	private Week getWeek(Sheet sheet, Cell cell, int year)
	{
		Week week = null;
		Date date = null;

		for (int i = 0; i < 20; i++)
		{
			// look for a column with dates

			date = getDate(cell, year);

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
				week = new Week(weekNumber, date);
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
