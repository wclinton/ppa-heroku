package com.ppa8ball.stats.service;

import java.util.List;


import com.ppa8ball.stats.PlayerStat;
import com.ppa8ball.stats.PlayersStat;
import com.ppa8ball.stats.Stats;
import com.ppa8ball.stats.TeamStat;

public class PlayerServiceImpl implements PlayerService
{

	public PlayerStat GetPlayer(Long id)
	{
		// TODO - fill this in so that it returns a player by id.

		return null;
	}

	public PlayersStat GetPlayerByTeam(int teamNumber)
	{

		List<PlayerStat> playerStats = getPlayersFromDB(teamNumber);

		if (playerStats.isEmpty())
		{
			loadDb();
			playerStats = getPlayersFromDB(teamNumber);
		}
		return new PlayersStat(playerStats);
	}

	private List<PlayerStat> getPlayersFromDB(int teamNumber)
	{
	//	return OfyService.myOfy().load().type(PlayerStat.class).filter("teamNumber", teamNumber).list();
		
		return null;
	}

	// TODO - this method should be moved somewhere else
	public static void loadDb()
	{
		Stats s = new Stats();

		s.load();

		List<PlayerStat> players = s.getPlayerStats();
//		OfyService.myOfy().save().entities(players).now();

		List<TeamStat> teams = s.getTeams();
//		OfyService.myOfy().save().entities(teams).now();
	}

	public PlayersStat GetSparePlayers()
	{
		return GetPlayerByTeam(11);
	}

	public void Save(PlayerStat playerStat)
	{
//		OfyService.myOfy().save().entity(playerStat).now();
	}

	public void Save(PlayersStat playersStat)
	{
		Save(playersStat.players);
	}

	public void Save(Iterable<PlayerStat> playersStat)
	{
		for (PlayerStat playerStat : playersStat)
		{
			Save(playerStat);
		}
	}
	
	public void DeleteAll()
	{
//		Iterable<Key<PlayerStat>> allKeys = OfyService.myOfy().load().type(PlayerStat.class).keys();
//		OfyService.myOfy().delete().keys(allKeys);		
	}
}
