package com.ppa8ball.service;

import com.ppa8ball.models.Season;

public interface SeasonService
{
	public void Save(Season season);
	public Season GetCurrent();
}
