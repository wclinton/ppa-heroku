package com.ppa8ball.stats;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class TeamStat implements Comparable<TeamStat>
{
	@Id public Long id;
	@Index public int number;
	public String name;
	@Index public boolean isSpare;
	@Index public boolean isNoPlayer;
	@Index public boolean isNormal;
	
	public TeamStat()
	{
		
	}

	public int compareTo(TeamStat o)
	{
		if (this.number < o.number)
			return -1;
		
		if (this.number > o.number)
			return 1;
		
		return 0;
	}
	
	public String toString()
	{
		return "Team:" + number + " " + name;
	}
}
