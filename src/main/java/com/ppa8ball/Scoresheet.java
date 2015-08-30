package com.ppa8ball;

import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.models.Team;
import com.ppa8ball.viewmodel.PlayerView;

public class Scoresheet
{

	private String date;
	private int week;
	private String table1;
	private String table2;

	private Team home;
	private Team away;

	private List<PlayerView> homePlayers;
	private List<PlayerView> awayPlayers;

	public Scoresheet(Team home, Team away)
	{
		this.home = home;
		this.away = away;
	}

	public Team getHome()
	{
		return home;
	}

	public void setHome(Team home)
	{
		this.home = home;
	}

	public Team getAway()
	{
		return away;
	}

	public void setAway(Team away)
	{
		this.away = away;
	}

	public List<PlayerView> getHomePlayers()
	{
		if (homePlayers == null)
			homePlayers =  new ArrayList<PlayerView>();
		return homePlayers;
	}

	public void setHomePlayers(List<PlayerView> homePlayers)
	{
		this.homePlayers = homePlayers;
	}

	public List<PlayerView> getAwayPlayers()
	{
		if (awayPlayers == null)
			awayPlayers = new ArrayList<PlayerView>();
		return awayPlayers;
	}

	public void setAwayPlayers(List<PlayerView> awayPlayers)
	{
		this.awayPlayers = awayPlayers;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public int getWeek()
	{
		return week;
	}

	public void setWeek(int week)
	{
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
