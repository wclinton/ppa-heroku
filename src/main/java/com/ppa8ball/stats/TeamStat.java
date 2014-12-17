package com.ppa8ball.stats;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class TeamStat
{
	@Id public Long id;
	public int number;
	public String name;
	
	public TeamStat()
	{
		
	}
}
