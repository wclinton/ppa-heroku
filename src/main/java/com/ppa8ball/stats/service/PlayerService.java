package com.ppa8ball.stats.service;

import com.ppa8ball.stats.PlayerStat;
import com.ppa8ball.stats.PlayersStat;

public interface PlayerService
{
	public PlayersStat GetPlayerByTeam(int teamNumber);
	
	public PlayersStat GetSparePlayers();
	
	public void Save(PlayerStat playerStat);
	
	public void Save(Iterable<PlayerStat> playerStats);
	
	public void Save(PlayersStat playersStat);
	
	public void DeleteAll();
}
