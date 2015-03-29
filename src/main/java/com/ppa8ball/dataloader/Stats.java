package com.ppa8ball.dataloader;

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

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Stat;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamType;
import com.ppa8ball.stats.CellHelp;
import com.ppa8ball.stats.Gender;

public class Stats
{
	private final String StatsUrl = "http://www.ppa8ball.com/stats/";

	//private final int teamNumberColumn = ColumnToInt("A");
	private final int genderColumn = ColumnToInt("C");
	private final int firstNameColumn = ColumnToInt("D");
	private final int lastNameColumn = ColumnToInt("E");
	private final int fullNameColumn = ColumnToInt("F");
	private final int totalPointsColumn = ColumnToInt("Z");
	private final int adjustedAverageColumn = ColumnToInt("AA");
	private final int actualAverageColumn = ColumnToInt("AC");
	private final int gamesPlayedColumn = ColumnToInt("AB");
	private final int perfectNightsColumn = ColumnToInt("AD");

	
	private final Season season;
	private  Sheet sheet;

	public Stats(Season season)
	{
		this.season = season;
		Workbook workbook;
		try
		{
			workbook = Workbook.getWorkbook(getExcelSpreadSheet());
			sheet = workbook.getSheet(1);
			return;
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

//	public void load()
//	{
//		try
//		{
//			
//			Workbook workbook = Workbook.getWorkbook(getExcelSpreadSheet());
//
//			Sheet sheet = workbook.getSheet(1);
//
//			getStats(sheet);
//
//		} catch (BiffException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public TeamStat getTeam(int number)
//	{
//		for (TeamStat team : teams)
//		{
//			if (team.number == number)
//				return team;
//		}
//		return null;
//	}

	

//	public List<PlayerStat> getPlayerStat(int teamNumber, int[] playerOrder)
//	{
//		List<PlayerStat> teamPlayers = new ArrayList<PlayerStat>();
//
//		for (PlayerStat playerStat : players)
//		{
//			if (playerStat.teamNumber == teamNumber)
//				teamPlayers.add(playerStat);
//		}
//
//		if (playerOrder == null)
//			return teamPlayers;
//
//		return sortPlayers(teamPlayers, playerOrder);
//	}

//	public List<PlayerStat> getPlayerStats()
//	{
//		return players;
//	}
//
//	private List<PlayerStat> sortPlayers(List<PlayerStat> players, int[] playerOrder)
//	{
//
//		List<PlayerStat> sortedPlayers = new ArrayList<PlayerStat>();
//
//		for (int index : playerOrder)
//		{
//			sortedPlayers.add(players.get(index - 1));
//		}
//
//		return sortedPlayers;
//
//	}

	public List<Team> loadTeams()
	{
		List<Team> teams = new ArrayList<Team>();

		Team lastTeam=null;
		Cell cell = sheet.getCell("A2");

		while (cell.getContents() == null || cell.getContents() != "")
		{
			Player player = getPlayerStat(sheet, cell.getRow());
			
			Team team = getTeam(sheet, cell);
			
			if (lastTeam == null || lastTeam.getNumber() != team.getNumber())
			{
				teams.add(team);
				lastTeam = team;
			}
			
			lastTeam.getPlayers().add(player);
			cell = sheet.getCell(cell.getColumn(), cell.getRow() + 1);
		}
		
		return teams;
	}
	
//	public List<Player> loadPlayersAndStats(List<Team> teams)
//	{
//		
//		List<Player> players = new ArrayList<Player>();
//		
//		Cell cell = sheet.getCell("A2");
//
//		while (cell.getContents() == null || cell.getContents() != "")
//		{			
//			Player player = getPlayerStat(teams,sheet, cell.getRow());
//
//			players.add(player);
//
//			cell = sheet.getCell(cell.getColumn(), cell.getRow() + 1);
//		}
//		
//		return players;
//	}

	private Team getTeam(Sheet sheet, Cell cell)
	{
		int number = Integer.parseInt(cell.getContents());
		String name = CellHelp.getCellToRight(sheet, cell).getContents();

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
	
//	List<Player> getPlayersAndStats()
//	{
//		private List<Player> getPlayersAndStats(Sheet sheet)
////		{
////			Cell cell = sheet.getCell("A2");
//	}

//	private List<Player> getPlayersAndStats(Sheet sheet)
//	{
//		Cell cell = sheet.getCell("A2");
//
//		while (cell.getContents() == null || cell.getContents() != "")
//		{
//
//			TeamStat team = getTeamStat(sheet, cell);
//			if (!teams.isEmpty())
//			{
//				TeamStat lastTeam = teams.get(teams.size() - 1);
//				if (lastTeam.number != team.number)
//					teams.add(team);
//			}
//
//			else
//				teams.add(team);
//
//			PlayerStat player = getPlayerStat(sheet, cell.getRow(), team.number);
//
//			players.add(player);
//
//			cell = sheet.getCell(cell.getColumn(), cell.getRow() + 1);
//		}
//	}

//	private TeamStat getTeamStat(Sheet sheet, Cell cell)
//	{
//		TeamStat team = new TeamStat();
//		team.number = Integer.parseInt(cell.getContents());
//		Cell nameCell = CellHelp.getCellToRight(sheet, cell);
//		team.name = nameCell.getContents();
//		team.isSpare = team.name.equalsIgnoreCase("spare");
//		team.isNoPlayer = team.name.equalsIgnoreCase("No Player");
//		team.isNormal = (!team.isNoPlayer && !team.isSpare);
//		return team;
//	}

	private Player getPlayerStat(Sheet sheet, int row)
	{
		
		
		
		String firstName = sheet.getCell(firstNameColumn, row).getContents().trim();
		String lastName = sheet.getCell(lastNameColumn, row).getContents().trim();
		String fullName = sheet.getCell(fullNameColumn, row).getContents().trim();
		Gender gender = getGender(sheet.getCell(genderColumn, row).getContents());
		Player player = new Player(firstName, lastName, fullName, gender);
	
		double actualAverage = getDecimalValue(sheet.getCell(actualAverageColumn, row));
		double adjustedAverage = getDecimalValue(sheet.getCell(adjustedAverageColumn, row));

		int totalPoints = Integer.parseInt(sheet.getCell(totalPointsColumn, row).getContents());
		int gamesPlayed = Integer.parseInt(sheet.getCell(gamesPlayedColumn, row).getContents());
		int perfectNights = Integer.parseInt(sheet.getCell(perfectNightsColumn, row).getContents());
		
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
		return new URL(StatsUrl + year + "/week" + String.format("%02d", week) + ".xls");
	}

	static boolean urlExists(URL url)
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
	
	static Team getTeamByNumber(List<Team>teams, int teamNumber)
	{
		for (Team team : teams)
		{
			if (team.getNumber() == teamNumber)
				return team;
		}
		return null;
	}
}
