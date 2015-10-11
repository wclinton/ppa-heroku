package com.ppa8ball.service;

import com.ppa8ball.models.Roster;

public interface RosterService
{
	public Roster getByTeam(long seasonId, long teamId);
	public void Save(Roster roster);
}
