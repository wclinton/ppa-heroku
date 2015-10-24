package com.ppa8ball.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Stat
{
	@GeneratedValue
	@Id
	private long id;

	private int totalPoints;
	private int gamesPlayed;
	private double adjustedAverage;
	private double actualAverage;
	private int perfectNights;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Season season;

	public Stat()
	{
	}

	public Stat(int totalPoints, int gamesPlayed, Double adjustedAverage, Double actualAverage, int perfectNights, Season season)
	{
		super();
		this.totalPoints = totalPoints;
		this.gamesPlayed = gamesPlayed;
		this.adjustedAverage = adjustedAverage;
		this.actualAverage = actualAverage;
		this.perfectNights = perfectNights;
		this.season = season;
	}

	public long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Season getSeason()
	{
		return season;
	}

	public void setSeason(Season season)
	{
		this.season = season;
	}

	public int getTotalPoints()
	{
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints)
	{
		this.totalPoints = totalPoints;
	}

	public int getGamesPlayed()
	{
		return gamesPlayed;
	}

	public void setGamesPlayed(Integer gamesPlayed)
	{
		this.gamesPlayed = gamesPlayed;
	}

	public double getAdjustedAverage()
	{
		return adjustedAverage;
	}

	public void setAdjustedAverage(Double adjustedAverage)
	{
		this.adjustedAverage = adjustedAverage;
	}

	public double getActualAverage()
	{
		return actualAverage;
	}

	public void setActualAverage(Double actualAverage)
	{
		this.actualAverage = actualAverage;
	}

	public int getPerfectNights()
	{
		return perfectNights;
	}

	public void setPerfectNights(Integer perfectNights)
	{
		this.perfectNights = perfectNights;
	}

	public double getDisplayAdjustedAverage()
	{
		return RoundTo1Decimals(adjustedAverage);
	}
	
	public double getDisplayActualAverage()
	{
		return RoundTo1Decimals(actualAverage);
	}
	
	private static double RoundTo1Decimals(double d)
	{
		double rounded = Math.round(10 * d);
		return rounded / 10;
	}
}
