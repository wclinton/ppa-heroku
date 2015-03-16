package com.ppa8ball.viewmodel;

import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.models.Week;

public class WeeksView
{
	
	private List<WeekView> weeks;
	
	public WeeksView()
	{
	}
	
	public WeeksView(List<Week> weeks)
	{
		this.weeks = new ArrayList<WeekView>();
		
		for (Week week : weeks)
		{
			this.weeks.add(new WeekView(week));
		}
	}

	public List<WeekView> getWeeks()
	{
		return weeks;
	}

	public void setWeeks(List<WeekView> weeks)
	{
		this.weeks = weeks;
	}

}
