package com.ppa8ball.viewmodel;

import java.util.ArrayList;
import java.util.List;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Roster;

public class RosterView
{
	public long id;
	public int teamId;
	public PlayerView [] rosterPlayers;
	public boolean isHome;
	
	public RosterView()
	{
	}
	
	public RosterView(Roster roster)
	{
		this.isHome = roster.isHome();
		this.teamId = roster.getTeam().getNumber();
		this.id = roster.getId();
		
		List<PlayerView> players = new ArrayList<PlayerView>();
		
		for (Player player : roster.getPlayers()) {
			
			PlayerView playerView = new PlayerView(player, player.getStats(roster.getSeason()));
			players.add(playerView);
		}
		rosterPlayers =  players.stream().toArray(PlayerView[]::new);
		
	}
}