package com.ppa8ball.stats;


public class PlayerStat {

	public Long id;
	public int teamNumber;
	public String firstName;
	public String lastName;
	public String fullName;
	
	public Gender gender;
	
	public int totalPoints;
	public int gamesPlayed;
	public double adjustedAverage;
	public double actualAverage;
	public int perfectNights;
	
	public double displayAdjustedAverage;
	public double displayActualAverage;
	

	public PlayerStat()
	{
	}
	
	void onLoad() 
	 {
		 displayActualAverage = RoundTo1Decimals(actualAverage);
		 displayAdjustedAverage = RoundTo1Decimals(adjustedAverage);
	 }
	 
	 public String toString()
	 {
		 return "Player:" + fullName + " Team:" + teamNumber;
	 }
	 
	 private static double RoundTo1Decimals(double d)
	 {
		 double rounded = Math.round(10* d);
		 return rounded / 10;
	 }
}
