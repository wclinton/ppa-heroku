package com.ppa8ball.schedule;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
@Entity
public class Match
{
	@Id public Long id;
	@Index public String season;
	@Index public int week;
	@Index public int match;
	@Index public int homeTeam;
	@Index public int awayTeam;
	
	
	public Match()
	{
		
	}
	
	public String toString()
	{
		return  awayTeam + " at " + homeTeam;
	}
}
