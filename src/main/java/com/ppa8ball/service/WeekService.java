package com.ppa8ball.service;

import java.util.List;

import com.ppa8ball.models.Season;
import com.ppa8ball.models.Week;

public interface WeekService
{
	public void Save (List<Week> weeks);
	public void Save (Week week);
	public Week getWeekbyNumber(Season season,int number);
	public List<Week> getAll(Season season);
	public List<Week> getAll();
}
