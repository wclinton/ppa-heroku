package com.ppa8ball.schedule;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Schedule
{
	public String season;
	public List<Week> weeks;
	
	
	public Schedule()
	{
		weeks = new ArrayList<Week>();
	}
}
