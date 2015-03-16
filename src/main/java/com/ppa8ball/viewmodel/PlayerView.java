package com.ppa8ball.viewmodel;

import java.util.List;

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Stat;

public class PlayerView
{
	
	private long id;
	//private int teamNumber;
	private String firstName;
	private String lastName;
	private String fullName;
	private String gender;
	private int totalPoints;
	private int gamesPlayed;
	private double adjustedAverage;
	private double actualAverage;
	private int perfectNights;
	private double displayAdjustedAverage;
	private double displayActualAverage;
	
	public PlayerView(Player player)
	{
		id = player.getId();
		firstName = player.getFirstName();
		lastName = player.getLastName();
		fullName = player.getFullName();
		gender = player.getGender().toString();
		
		Stat stat = getCurrentPlayerStat(player.getStats());
		
		totalPoints = stat.getTotalPoints();
		gamesPlayed = stat.getGamesPlayed();
		adjustedAverage = stat.getAdjustedAverage();
		actualAverage = stat.getActualAverage();
		perfectNights = stat.getPerfectNights();
		displayActualAverage = stat.getDisplayActualAverage();
		displayAdjustedAverage = stat.getDisplayAdjustedAverage();
	}
	
	public long getId()
	{
		return id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public String getGender()
	{
		return gender;
	}

	public int getTotalPoints()
	{
		return totalPoints;
	}

	public int getGamesPlayed()
	{
		return gamesPlayed;
	}

	public double getAdjustedAverage()
	{
		return adjustedAverage;
	}

	public double getActualAverage()
	{
		return actualAverage;
	}

	public int getPerfectNights()
	{
		return perfectNights;
	}

	public double getDisplayAdjustedAverage()
	{
		return displayAdjustedAverage;
	}

	public double getDisplayActualAverage()
	{
		return displayActualAverage;
	}

	private static Stat getCurrentPlayerStat(List<Stat> stats)
	{
		return stats.get(0);
	}
}