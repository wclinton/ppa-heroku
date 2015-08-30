package com.ppa8ball.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.models.Team;

public class TeamsView
{
	public List<TeamView> teams = new ArrayList<TeamView>();
	
	public TeamsView(List<Team> teams)
	{
		for (Team team : teams)
		{
			this.teams.add(new TeamView(team));
		}
	}
}
