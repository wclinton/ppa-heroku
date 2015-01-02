package com.ppa8ball.stats;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class TeamStat
{
	@Id public Long id;
	@Index public int number;
	public String name;
	
	public TeamStat()
	{
		
	}
}
