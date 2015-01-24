package com.ppa8ball;

public class Scoresheet {

	
	private TeamRoster homeTeam;
	private TeamRoster awayTeam;
	
	private String date;
	private int week;
	private String table1;
	private String table2;
	
	public Scoresheet(TeamRoster homeTeam, TeamRoster awayTeam)
	{
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	public TeamRoster getHomeTeam()
	{
		return homeTeam;
	}
	
	public TeamRoster getAwayTeam()
	{
	return awayTeam;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}
	
	public String getTable1()
	{
		return table1;
	}
	
	public String getTable2()
	{
		return table2;
	}
	
	public void setTable1(String table)
	{
		table1 = table;
	}
	public void setTable2(String table)
	{
		table2 = table;
	}
}
