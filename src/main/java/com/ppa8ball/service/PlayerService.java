package com.ppa8ball.service;

import java.util.List;

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Team;

public interface PlayerService
{
	public void Save(Player player);
	public void Save(List<Player> players);
	
	public Player Get(long id);
	public List<Player> GetByTeam(Team team);
	public List<Player>GetSpare();
	public List<Player>Get();
	public Player GetByName(String firstName, String lastName);
}
