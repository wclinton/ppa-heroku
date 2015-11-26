package com.ppa8ball.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class PlayersView
{
	private List<PlayerView> players;
	
	public PlayersView()
	{
		players = new ArrayList<PlayerView>();
	}

	public List<PlayerView> getPlayers()
	{
		return players;
	}

	public void setPlayers(List<PlayerView> players)
	{
		this.players = players;
	}
	
	public void addPlayers(PlayersView players)
	{
		this.players.addAll(players.getPlayers());
	}
}
