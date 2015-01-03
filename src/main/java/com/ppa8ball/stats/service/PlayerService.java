package com.ppa8ball.stats.service;

import com.ppa8ball.stats.PlayersStat;

public interface PlayerService
{
	public PlayersStat GetPlayerByTeam(int teamNumber);
	
	public PlayersStat GetSparePlayers();
}
