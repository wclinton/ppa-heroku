package com.ppa8ball.stats;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Unindex;

@Entity
public class PlayerStat {

	@Id public Long id;
	public int teamNumber;
	public String firstName;
	public String lastName;
	public String fullName;
	
	public Gender gender;
	
	@Unindex public int totalPoints;
	@Unindex public int gamesPlayed;
	@Unindex public double adjustedAverage;
	@Unindex public double actualAverage;
	@Unindex public int perfectNights;
	
	public PlayerStat()
	{
		
	}
}
