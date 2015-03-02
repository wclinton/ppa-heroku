package com.ppa8ball.stats;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Stats
{

	private final String StatsUrl = "http://www.ppa8ball.com/stats/";

	private final int genderColumn = ColumnToInt("C");
	private final int firstNameColumn = ColumnToInt("D");
	private final int lastNameColumn = ColumnToInt("E");
	private final int fullNameColumn = ColumnToInt("F");
	private final int totalPointsColumn = ColumnToInt("Z");
	private final int adjustedAverage = ColumnToInt("AA");
	private final int actualAverage = ColumnToInt("AC");
	private final int gamesPlayed = ColumnToInt("AB");
	private final int perfectNights = ColumnToInt("AD");

	private List<TeamStat> teams = new ArrayList<TeamStat>();
	private List<PlayerStat> players = new ArrayList<PlayerStat>();

	public void load()
	{
		try
		{
			Workbook workbook = Workbook.getWorkbook(getExcelSpreadSheet());

			Sheet sheet = workbook.getSheet(1);

			getStats(sheet);

		} catch (BiffException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TeamStat getTeam(int number)
	{
		for (TeamStat team : teams)
		{
			if (team.number == number)
				return team;
		}
		return null;
	}

	public List<TeamStat> getTeams()
	{
		return teams;
	}

	public List<PlayerStat> getPlayerStat(int teamNumber, int[] playerOrder)
	{
		List<PlayerStat> teamPlayers = new ArrayList<PlayerStat>();

		for (PlayerStat playerStat : players)
		{
			if (playerStat.teamNumber == teamNumber)
				teamPlayers.add(playerStat);
		}

		if (playerOrder == null)
			return teamPlayers;

		return sortPlayers(teamPlayers, playerOrder);
	}

	public List<PlayerStat> getPlayerStats()
	{
		return players;
	}

	private List<PlayerStat> sortPlayers(List<PlayerStat> players, int[] playerOrder)
	{

		List<PlayerStat> sortedPlayers = new ArrayList<PlayerStat>();

		for (int index : playerOrder)
		{
			sortedPlayers.add(players.get(index - 1));
		}

		return sortedPlayers;

	}

	private void getStats(Sheet sheet)
	{
		Cell cell = sheet.getCell("A2");

		while (cell.getContents() == null || cell.getContents() != "")
		{

			TeamStat team = getTeamStat(sheet, cell);
			if (!teams.isEmpty())
			{
				TeamStat lastTeam = teams.get(teams.size() - 1);
				if (lastTeam.number != team.number)
					teams.add(team);
			}

			else
				teams.add(team);

			PlayerStat player = getPlayerStat(sheet, cell.getRow(), team.number);

			players.add(player);

			cell = sheet.getCell(cell.getColumn(), cell.getRow() + 1);
		}
	}

	TeamStat getTeamStat(Sheet sheet, Cell cell)
	{
		TeamStat team = new TeamStat();
		team.number = Integer.parseInt(cell.getContents());
		Cell nameCell = CellHelp.getCellToRight(sheet, cell);
		team.name = nameCell.getContents();
		team.isSpare = team.name.equalsIgnoreCase("spare");
		team.isNoPlayer = team.name.equalsIgnoreCase("No Player");
		team.isNormal = (!team.isNoPlayer && !team.isSpare);
		return team;
	}

	PlayerStat getPlayerStat(Sheet sheet, int row, int teamNumber)
	{
		PlayerStat player = new PlayerStat();

		player.teamNumber = teamNumber;

		player.firstName = sheet.getCell(firstNameColumn, row).getContents().trim();
		player.lastName = sheet.getCell(lastNameColumn, row).getContents().trim();
		player.fullName = sheet.getCell(fullNameColumn, row).getContents().trim();

		player.actualAverage = getDecimalValue(sheet.getCell(actualAverage, row));
		player.adjustedAverage = getDecimalValue(sheet.getCell(adjustedAverage, row));

		player.totalPoints = Integer.parseInt(sheet.getCell(totalPointsColumn, row).getContents());
		player.gamesPlayed = Integer.parseInt(sheet.getCell(gamesPlayed, row).getContents());
		player.perfectNights = Integer.parseInt(sheet.getCell(perfectNights, row).getContents());

		player.gender = getGender(sheet.getCell(genderColumn, row).getContents());

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

	private double getDecimalValue(Cell cell)
	{
		CellType type = cell.getType();

		if (type != CellType.NUMBER_FORMULA && type != CellType.NUMBER)
		{
			return 0.0;
		}

		NumberCell nc = (NumberCell) cell;
		return nc.getValue();

	}

	private Gender getGender(String s)
	{

		if (s.compareToIgnoreCase("M") == 0 || s.compareToIgnoreCase("male") == 0)
			return Gender.Male;

		else if (s.compareToIgnoreCase("F") == 0 || s.compareToIgnoreCase("female") == 0)

			return Gender.Female;

		return Gender.Unknown;
	}

	private InputStream getExcelSpreadSheet()
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

	private URL getStatsUrl(int year, int week) throws MalformedURLException
	{
		return new URL(StatsUrl + year + "/week" + String.format("%02d", week)  + ".xls");
	}

	static boolean urlExists(URL url)
	{
		try
		{
			//HttpURLConnection.setFollowRedirects(false);
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
