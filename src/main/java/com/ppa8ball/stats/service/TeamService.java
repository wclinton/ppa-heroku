package com.ppa8ball.stats.service;

import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.TeamsStat;

public interface TeamService
{
	public TeamsStat GetAll();	
	public TeamStat Get(int teamNumber);
	
	public void Save(TeamStat team);
	
	public void Save(Iterable<TeamStat> teams);
	
	public void DeleteAll();
}
