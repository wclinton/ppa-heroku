package com.ppa8ball;

import java.util.List;

public class Roster
{
	public static String ScoreSheetUrl = "http://www.ppa8ball.com/Scoresheet.pdf";

	/** The resulting PDF. */
	public static final String RESULT1 = "scoresheet.pdf";

//private static int week;
	private static boolean isHome;
	private static int homeTeamNo;
	private static int AwayTeamNo;
	private static int[] playerOrder;

	public static void main(String[] args)
	{

//		try
//		{
			
			if (!parseArgs(args))
				return;
			Stats s = new Stats();

			s.load();

			TeamStat homeTeamStat = s.getTeam(homeTeamNo);
		

			TeamStat awayTeamStat = s.getTeam(AwayTeamNo);

			TeamRoster homeTeamRoster;
			TeamRoster awayTeamRoster;

			if (isHome)
			{
				List<PlayerStat> homePlayersStat = s.getPlayerStat(homeTeamNo,playerOrder);
				homeTeamRoster = new TeamRoster(homeTeamStat, homePlayersStat);
				awayTeamRoster = new TeamRoster(awayTeamStat);
			}
			else
			{
				List<PlayerStat> awayPlayersStats = s.getPlayerStat(AwayTeamNo,playerOrder);
				homeTeamRoster = new TeamRoster(homeTeamStat);
				awayTeamRoster = new TeamRoster(awayTeamStat,awayPlayersStats);
			}
			Scoresheet scoresheet = new Scoresheet(homeTeamRoster, awayTeamRoster);

			//ScoresheetGenerator.generateScoreSheet("Dec 10-2014", week, scoresheet, RESULT1);
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DocumentException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	static boolean parseArgs(String[] args)
	{

		// command line arguments are Week Home/Away HomeTeamNo AwayTeamNo
		// *Player1 *PLayer2 *Player3 *PLayer4 *PLayer5

		if (args.length < 4 || args.length > 9)
		{
			showHelp();
			return false;
		}

		int i = 0;

	//	week = Integer.parseInt(args[i++]);
		isHome = args[i++].compareToIgnoreCase("h") == 0;

		homeTeamNo = Integer.parseInt(args[i++]);

		AwayTeamNo = Integer.parseInt(args[i++]);

		int j = 0;
		while (i < args.length)
		{
			if (playerOrder == null)
				playerOrder = new int[5];
			playerOrder[j++] = Integer.parseInt(args[i++]);
		}
		
		return true;
	}

	static void showHelp()
	{
		System.out.println("Roster");

		System.out.println("Command Line Arguements:");

	}

	// static private Scoresheet GetTestScoresheet()
	// {
	//
	// TeamRoster home = GetTestTeamRoster("Home Team", new String[]
	// { "Wade", "Troy", "Gerald", "Sharon", "Brad" }, new double[]
	// { 1.1, 2.2, 3.3, 4.4, 5.0 });
	// TeamRoster away = GetTestTeamRoster("Away Team", new String[]
	// { "Neil", "John", "Allen", "Michelle", "Frank" }, new double[]
	// { 6, 7, 8, 9, 10 });
	//
	// Scoresheet scoresheet = new Scoresheet(home, away);
	//
	// return scoresheet;
	// }

	// static private TeamRoster GetTestTeamRoster(String name, String[]
	// players, double[] averages)
	// {
	// TeamRoster roster = new TeamRoster();
	// roster.name = name;
	//
	// for (int i = 0; i < 5; i++)
	// {
	// roster.players[i] = new Player(players[i], averages[i]);
	// }
	//
	// return roster;
	// }
}
