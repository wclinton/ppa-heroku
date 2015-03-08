package com.ppa8ball.stats;

public class TeamStat implements Comparable<TeamStat>
{
	public Long id;
	public int number;
	public String name;
	public boolean isSpare;
	public boolean isNoPlayer;
	public boolean isNormal;

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
