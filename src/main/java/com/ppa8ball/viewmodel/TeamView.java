package com.ppa8ball.viewmodel;

import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamType;

public class TeamView
{
	public String name;
	public int number;
	public TeamType type;
	
	public TeamView(Team team)
	{
		this.name = team.getName();
		this.number = team.getNumber();
		this.type = team.getType();
	}
}
