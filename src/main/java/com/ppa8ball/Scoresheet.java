package com.ppa8ball;

public class Scoresheet {

	
	private TeamRoster homeTeam;
	private TeamRoster awayTeam;
	
	private String date;
	private String week;
	
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

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
}
