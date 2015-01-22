package com.ppa8ball.schedule.service;

import java.util.List;

import com.ppa8ball.schedule.Week;

public interface WeekService
{
	public List<Week> GetAll();
	public void Save(Week week);
	public void Save(Iterable<Week> weeks);
	
	public void DeleteAll();
}
