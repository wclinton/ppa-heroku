package com.ppa8ball.schedule;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.ppa8ball.excel.MySheet;
import com.ppa8ball.excel.PPACell;
import com.ppa8ball.excel.Sheet;
import com.ppa8ball.models.Match;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;

public class ScheduleLoader implements DataLoader
{
	private final String PPAUrl = "http://www.ppa8ball.com";
	private final int FirstHalfYear = 2014;
	private final int SecondHalfYear = 2015;
	private final TeamService teamService;
	private final Season currentSeason;

	public ScheduleLoader(Session session, Season season)
	{
		teamService = new TeamServiceImpl(session);
		currentSeason = new SeasonServiceImpl(session).GetCurrent();
	}

	public List<Week> Load()
	{
		Sheet sheet = new MySheet(getExcelSpreadSheet(getFirstHalfScheduleUrl(currentSeason)), 0);
		List<Week> firstHalfWeeks = getWeeks(sheet, FirstHalfYear);

		sheet = new MySheet(getExcelSpreadSheet(getSecondHalfScheduleUrl(currentSeason)), 0);
		List<Week> SecondHalfWeeks = getWeeks(sheet, SecondHalfYear);

		firstHalfWeeks.addAll(SecondHalfWeeks);

		return firstHalfWeeks;
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
		PPACell startCell = sheet.getCell(0, 2);
		PPACell cell = startCell;

		List<Week> weeks = new ArrayList<>();

		while (weeks.size() < 9)
		{
			Week week = getWeek(cell, year);

			if (week != null)
			{
				while (week.getMatches().size() < 5)
				{
					cell = cell.getCellBelow();
					Match match = getMatch(week.getMatches().size() + 1, sheet, cell);

					if (match != null)
					{
						match.setWeek(week);
						week.getMatches().add(match);
					}
				}
				weeks.add(week);
			}
			cell = cell.getNextTopColumn();
		}

		return weeks;

	}

	private Match getMatch(int number, Sheet sheet, PPACell cell)
	{
		String contents = cell.getStringValue();

		if (contents.contains("at"))
		{
			String[] teams = contents.split("at");

			int homeTeamNumber = Integer.parseInt(teams[1].trim());
			int awayTeamNumber = Integer.parseInt(teams[0].trim());

			Team home = teamService.GetByNumber(currentSeason.getId(), homeTeamNumber);
			Team away = teamService.GetByNumber(currentSeason.getId(), awayTeamNumber);

			cell = cell.getCellBelow();

			String[] tables = getTables(cell);

			return new Match(currentSeason, number, tables[0], tables[1], home, away);
		}
		return null;
	}

	private String[] getTables(PPACell cell)
	{
		String contents = cell.getStringValue();

		if (contents.contains("Table"))
		{
			String[] tables = contents.substring("Tables ".length()).split("&");

			tables[0] = tables[0].trim();
			tables[1] = tables[1].trim();

			return tables;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private Date getDate(PPACell cell, int year)
	{
		String contents = cell.getStringValue();
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

	private int getWeekDate(PPACell cell)
	{
		if (!cell.IsString())
			return -1;

		String contents = cell.getStringValue();

		if (contents == null || !contents.startsWith("Week"))
			return -1;

		String weekString = contents.substring(5);

		int week = Integer.parseInt(weekString);

		return week;
	}

	private Week getWeek(PPACell cell, int year)
	{
		Week week = null;
		Date date = null;

		for (int i = 0; i < 20; i++)
		{
			// look for a column with dates

			if (cell.IsDate())
			{
				date = cell.getDateValue();
				break;
			}

			cell = cell.getCellBelow();
		}

		if (date != null)
		{
			PPACell weekCell = cell.getCellBelow();

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
