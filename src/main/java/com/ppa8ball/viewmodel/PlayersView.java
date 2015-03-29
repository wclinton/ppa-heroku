package com.ppa8ball.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class PlayersView
{
	private List<PlayerView> players;
	
	public PlayersView()
	{
		
	}

	public List<PlayerView> getPlayers()
	{
		if (players == null)
			players = new ArrayList<PlayerView>();
		return players;
	}

	public void setPlayers(List<PlayerView> players)
	{
		this.players = players;
	}
}
