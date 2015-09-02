package com.ppa8ball.dataloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.excel.MySheet;
import com.ppa8ball.excel.PPACell;
import com.ppa8ball.excel.Sheet;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Stat;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamType;
import com.ppa8ball.stats.Gender;

public class Stats
{
	private final static String StatsUrl = "http://www.ppa8ball.com/stats/";

	private final static int teamNumberColumn = ColumnToInt("A");
	private final static int genderColumn = ColumnToInt("C");
	private final static int firstNameColumn = ColumnToInt("D");
	private final static int lastNameColumn = ColumnToInt("E");
	private final static int fullNameColumn = ColumnToInt("F");
	private final static int totalPointsColumn = ColumnToInt("Z");
	private final static int adjustedAverageColumn = ColumnToInt("AA");
	private final static int actualAverageColumn = ColumnToInt("AC");
	private final static int gamesPlayedColumn = ColumnToInt("AB");
	private final static int perfectNightsColumn = ColumnToInt("AD");

	public static List<Team> LoadSeasonStats(Season season)
	{
		Sheet sheet = new MySheet(getExcelSpreadSheet(), 1);
		return loadTeamsAndPlayers(season, sheet);
	}

	private static List<Team> loadTeamsAndPlayers(Season season, Sheet sheet)
	{
		List<Team> teams = new ArrayList<Team>();

		Team lastTeam = null;
		PPACell cell = sheet.getCell(1, teamNumberColumn);

		while (cell.getIntValue() != 12)
		{
			Player player = getPlayerStat(season, sheet, cell.getRowIndex());

			Team team = getTeam(season, sheet, cell);

			if (lastTeam == null || lastTeam.getNumber() != team.getNumber())
			{
				teams.add(team);
				lastTeam = team;
			}

			lastTeam.getPlayers().add(player);
			cell = cell.getCellBelow();
		}

		Team noPlayer = getTeam(season, sheet, cell);

		teams.add(noPlayer);
		return teams;
	}

	private static Team getTeam(Season season, Sheet sheet, PPACell cell)
	{
		int number = cell.getIntValue();
		String name = cell.getCellToRight().getStringValue();

		final TeamType type;

		if (name.equalsIgnoreCase("spare"))
		{
			type = TeamType.Spare;
		} else if (name.equalsIgnoreCase("No Player"))
		{
			type = TeamType.NoPlayer;
		} else
			type = TeamType.Normal;

		return new Team(season, name, number, type);
	}

	private static Player getPlayerStat(Season season, Sheet sheet, int row)
	{

		String firstName = sheet.getCell(row, firstNameColumn).getStringValue();
		String lastName = sheet.getCell(row, lastNameColumn).getStringValue().trim();
		String fullName = sheet.getCell(row, fullNameColumn).getStringValue().trim();
		Gender gender = getGender(sheet.getCell(row, genderColumn).getStringValue());
		Player player = new Player(firstName, lastName, fullName, gender);

		double actualAverage = sheet.getCell(row, actualAverageColumn).getDoubleValue();
		double adjustedAverage = sheet.getCell(row, adjustedAverageColumn).getDoubleValue();

		int totalPoints = sheet.getCell(row, totalPointsColumn).getIntValue();
		int gamesPlayed = sheet.getCell(row, gamesPlayedColumn).getIntValue();
		int perfectNights = sheet.getCell(row, perfectNightsColumn).getIntValue();

		Stat stat = new Stat(totalPoints, gamesPlayed, adjustedAverage, actualAverage, perfectNights, season);

		player.getStats().add(stat);

		return player;
	}

	private static int ColumnToInt(String s)
	{
		char c;

		if (s.length() == 1)
		{
			c = s.charAt(0);
			return c - 'A';
		}

		c = s.charAt(1);

		return ColumnToInt("Z") + 1 + c - 'A';
	}

	private static Gender getGender(String s)
	{

		if (s.compareToIgnoreCase("M") == 0 || s.compareToIgnoreCase("male") == 0)
			return Gender.Male;

		else if (s.compareToIgnoreCase("F") == 0 || s.compareToIgnoreCase("female") == 0)

			return Gender.Female;

		return Gender.Unknown;
	}

	private static InputStream getExcelSpreadSheet()
	{
		try
		{

			final int year = 2014;

			URL url = null;

			for (int i = 20; i > 0; i--)
			{
				url = getStatsUrl(year, i);

				if (urlExists(url))
					break;
			}

			if (url != null)
				return url.openStream();

			return null;
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

	private static URL getStatsUrl(int year, int week) throws MalformedURLException
	{
		return new URL(StatsUrl + year + "/week" + String.format("%02d", week) + ".xlsx");
	}

	private static boolean urlExists(URL url)
	{
		try
		{
			// HttpURLConnection.setFollowRedirects(false);
			// note : you may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
