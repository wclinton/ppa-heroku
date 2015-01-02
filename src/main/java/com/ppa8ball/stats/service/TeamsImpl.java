package com.ppa8ball.stats.service;

import java.util.List;

import com.google.appengine.api.datastore.Query;
import com.ppa8ball.OfyService;
import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.TeamsStat;

public class TeamsImpl implements TeamService
{
	public TeamsStat GetAll()
	{
		List<TeamStat> teams = getFromDB();
		
		if (teams.isEmpty())
		{
			PlayerServiceImpl.loadDb();
			teams = getFromDB();
		}
		return new TeamsStat(teams);
	}
	
	private List<TeamStat> getFromDB()
	{
		
		
		
		
		//return OfyService.myOfy().load().type(TeamStat.class).order("number").list();
		
		List<TeamStat> teams = OfyService.myOfy().load().type(TeamStat.class).order("number").list();
		
		return teams;
				
	//			Query<Animal> all = ofy.query(Animal.class);

	}

	public TeamStat Get(int teamNumber)
	{
		return null;
	}
}
