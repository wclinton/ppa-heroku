package com.ppa8ball.stats.service;

import java.util.List;

import com.ppa8ball.stats.Stats;
import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.TeamsStat;

public class TeamsImpl implements TeamService
{
	
	Stats stats = null;
	
	public TeamsStat GetAll()
	{
		TeamsStat teams = new TeamsStat();
		
		teams.teams = getStats().getTeams();
		
		return teams;
	}
	
	public TeamStat Get(int teamNumber)
	{
		TeamsStat teams = GetAll();
		
		for (TeamStat team : teams.teams)
		{
			if (team.number == teamNumber)
				return team;
		}
		
		return null;
	}
	
	private Stats getStats()
	{
		if (stats == null)
		{
			stats = new Stats();
			stats.load();
		}
	    return stats;
	}

	
}
