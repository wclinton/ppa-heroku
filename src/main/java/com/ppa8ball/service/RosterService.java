package com.ppa8ball.service;

import com.ppa8ball.models.Roster;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;

public interface RosterService
{
	public Roster getByTeam(Season season, Team team, boolean isHome);
	public void Save(Roster roster);
	public Roster Get(long id);
	public void Delete(long id);
}
