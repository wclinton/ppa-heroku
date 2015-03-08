package com.ppa8ball.stats.service;

import java.util.List;
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

	public TeamStat Get(int teamNumber)
	{
	//	List<TeamStat> teams = OfyService.myOfy().load().type(TeamStat.class).filter("number", teamNumber).list();
	//	return teams.get(0);
		
		return null;
	}

	private List<TeamStat> getFromDB()
	{
//		List<TeamStat> teams = OfyService.myOfy().load().type(TeamStat.class).filter("isNormal", true).list();
//		Collections.sort(teams);
//		return teams;
		return null;
	}

	public void DeleteAll()
	{
//		Iterable<Key<TeamStat>> allKeys = OfyService.myOfy().load().type(TeamStat.class).keys();
//
//		// Useful for deleting items
//		OfyService.myOfy().delete().keys(allKeys);
		
	}
	
	public void Save(TeamStat team)
	{
		//OfyService.myOfy().save().entity(team).now();
		
	}
	
	public void Save(Iterable<TeamStat> teams)
	{
		for (TeamStat teamStat : teams)
		{
			Save(teamStat);
		}	
	}
}