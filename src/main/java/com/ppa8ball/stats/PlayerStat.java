package com.ppa8ball.stats;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.OnLoad;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class PlayerStat {

	@Id public Long id;
	@Index public int teamNumber;
	@Index public String firstName;
	public String lastName;
	@Index public String fullName;
	
	public Gender gender;
	
	@Unindex public int totalPoints;
	@Unindex public int gamesPlayed;
	@Unindex public double adjustedAverage;
	@Unindex public double actualAverage;
	@Unindex public int perfectNights;
	
	@Ignore public double displayAdjustedAverage;
	@Ignore public double displayActualAverage;
	

	public PlayerStat()
	{
	}
	
	 @OnLoad void onLoad() 
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
