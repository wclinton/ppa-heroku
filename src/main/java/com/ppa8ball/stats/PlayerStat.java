package com.ppa8ball.stats;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
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
	
	public PlayerStat()
	{
		
	}
}
