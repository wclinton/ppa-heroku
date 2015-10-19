package com.ppa8ball.viewmodel;

import com.ppa8ball.models.Season;

public class SeasonView
{
	private long id;
	private int startYear;
	private int endYear;
	private String description;
	private boolean complete;
	
	public SeasonView(Season season)
	{
		this.id = season.getId();
		this.startYear = season.getStartYear();
		this.endYear = season.getEndYear();
		this.description = season.getDescription();
		this.complete = season.isComplete();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getStartYear()
	{
		return startYear;
	}

	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	public int getEndYear()
	{
		return endYear;
	}

	public void setEndYear(int endYear)
	{
		this.endYear = endYear;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean isComplete()
	{
		return complete;
	}

	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
}
