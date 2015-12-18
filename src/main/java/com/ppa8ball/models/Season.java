package com.ppa8ball.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ppa8ball.dateutil.Day;

@Entity
@Table
public class Season implements Comparable<Season>
{
	private long id;
	private int startYear;
	private int endYear;
	private String description;
	private Week lastStatWeek;
	private List<Week> weeks;

	// Default constructor
	public Season()
	{
	}

	public Season(int startYear)
	{
		this(startYear, null);
	}

	public Season(int startYear, List<Week> weeks)
	{
		super();

		this.startYear = startYear;

		if (weeks == null)
			this.weeks = new ArrayList<Week>();
		else
			this.weeks = weeks;
	}

	@Id
	@GeneratedValue
	@Column
	public long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@Column
	public int getStartYear()
	{
		return startYear;
	}

	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	@Column
	public int getEndYear()
	{
		if (endYear == 0)
		{
			endYear = startYear + 1;
		}
		return endYear;
	}

	public void setEndYear(int endYear)
	{
		this.endYear = endYear;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "SEASON_ID")
	public List<Week> getWeeks()
	{
		Collections.sort(weeks);
		return weeks;
	}

	public void setWeeks(List<Week> weeks)
	{
		this.weeks = weeks;
	}

	@Column
	public String getDescription()
	{
		if (description == null)
			description = getStartYear() + "-" + getEndYear();
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Transient
	public boolean isComplete()
	{
		return getLastStatWeek().getNumber() == 18;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	public Week getLastStatWeek()
	{
		return lastStatWeek;
	}

	public void setLastStatWeek(Week week)
	{
		this.lastStatWeek = week;
	}

	@Transient
	public boolean isCurrentStatsAvailble()
	{
		// get the last week with stats.
		int lastWeekNum = getLastStatWeek().getNumber();

		// next week to play will be +1 but indexed from zero so +1 -1 ...
		Week nextWeekToPlay = weeks.get(lastWeekNum);

		// Get the current date and time in PST - server may not be in PST.
		Day today = Day.TodayInPST();
		Day nextPlayDate = new Day(nextWeekToPlay.getDate());
		return (today.beforeOrOn(nextPlayDate));
	}
	
	@Transient
	public int getNextWeek()
	{
		//TODO this will fail at the end of the season
		
		if (!isComplete())
			return getLastStatWeek().getNumber()+1;
		return -1;
	}
	

	@Override
	public String toString()
	{
		return "Season:" + description;
	}

	@Override
	public int compareTo(Season o)
	{
		return this.startYear > o.startYear ? 1 : -1;
	}
}