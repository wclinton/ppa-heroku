package com.ppa8ball.service;

import java.util.List;

import com.ppa8ball.models.Match;

public interface MatchService
{
	public void Save(List<Match> matches);
	public void Save(Match match);
	public Match getMatchByWeekTeam(long seasonId, int weekNumber, int TeamNumber);
}
