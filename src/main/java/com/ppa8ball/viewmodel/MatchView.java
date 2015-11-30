package com.ppa8ball.viewmodel;

import com.ppa8ball.models.Match;

public class MatchView
{
	private int id;
	private int weekId;
	private int match;
	private int homeTeam;
	private int awayTeam;
	private String table1;
	private String table2;

	public int getId()
	{
		return id;
	}

	public int getWeekId()
	{
		return weekId;
	}

	public int getMatch()
	{
		return match;
	}

	public int getHomeTeam()
	{
		return homeTeam;
	}

	public int getAwayTeam()
	{
		return awayTeam;
	}

	public String getTable1()
	{
		return table1;
	}

	public String getTable2()
	{
		return table2;
	}
	
	public MatchView()
	{}

	public MatchView(Match match)
	{
		weekId = match.getWeek().getNumber();
		this.match = match.getNumber();
		homeTeam = match.getHome().getNumber();
		awayTeam = match.getAway().getNumber();
		table1 = match.getTable1();
		table2 = match.getTable2();
	}
}