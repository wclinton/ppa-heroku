package com.ppa8ball.viewmodel;

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Stat;

public class PlayerView
{

	private long idx;
	public long getIdx() {
		return idx;
	}

	public void setIdx(long idx) {
		this.idx = idx;
	}

	private long id;
	// private int teamNumber;
	private String firstName;
	private String lastName;
	private String fullName;
	private String gender;
	private int totalPoints = 0;
	private int gamesPlayed = 0;
	private double adjustedAverage = 0;
	private double actualAverage = 0;
	private int perfectNights = 0;
	private double displayAdjustedAverage = 0;
	private double displayActualAverage = 0;
	
	public PlayerView()
	{
		
	}

	public PlayerView(Player player, Stat stat)
	{
		id = player.getId();
		firstName = player.getFirstName();
		lastName = player.getLastName();
		fullName = player.getFullName();
		gender = player.getGender().toString();
		if (stat != null)
		{
			totalPoints = stat.getTotalPoints();
			gamesPlayed = stat.getGamesPlayed();
			adjustedAverage = stat.getAdjustedAverage();
			actualAverage = stat.getActualAverage();
			perfectNights = stat.getPerfectNights();
			displayActualAverage = stat.getDisplayActualAverage();
			displayAdjustedAverage = stat.getDisplayAdjustedAverage();
		}
	}
	
	public void setId(long id)
	{
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public void setAdjustedAverage(double adjustedAverage) {
		this.adjustedAverage = adjustedAverage;
	}

	public void setActualAverage(double actualAverage) {
		this.actualAverage = actualAverage;
	}

	public void setPerfectNights(int perfectNights) {
		this.perfectNights = perfectNights;
	}

	public void setDisplayAdjustedAverage(double displayAdjustedAverage) {
		this.displayAdjustedAverage = displayAdjustedAverage;
	}

	public void setDisplayActualAverage(double displayActualAverage) {
		this.displayActualAverage = displayActualAverage;
	}

	public long getId()
	{
		return id;
	}

	public String getFirstName()
	{
		if (firstName == null)
			return "";
		return firstName;
	}

	public String getLastName()
	{
		if (lastName == null)
			return "";
		return lastName;
	}

	public String getFullName()
	{
		if (fullName == null || fullName.isEmpty())
			return (getFirstName() + " " +getLastName()).trim();
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
}
