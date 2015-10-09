package com.ppa8ball.viewmodel;

import com.ppa8ball.models.Match;

public class MatchView
{
	public int id;
	public int weekId;
	public int match;
	public int homeTeam;
	public int awayTeam;
	public String table1;
	public String table2;
	
	
	public MatchView()
	{
	}
	
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
