package com.ppa8ball.schedule.service;

import com.ppa8ball.schedule.Match;

public interface MatchService
{
	public void Save(Match match);
	public void Save(Iterable<Match> matches);
	
	public void DeleteAll();
}
