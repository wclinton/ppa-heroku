package com.ppa8ball.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamStat implements Comparable<TeamStat>
{
	public int id;
	public int number;
	public String name;
	public boolean isSpare;
	public boolean isNoPlayer;
	public boolean isNormal;

	public TeamStat()
	{

	}

	public TeamStat(ResultSet rs)
	{
		try
		{
			id = rs.getInt("id");
			number = rs.getInt("number");
			name = rs.getString("name");
			isSpare = rs.getBoolean("isSpare");
			isNoPlayer = rs.getBoolean("isNoPlayer");
			isNormal = rs.getBoolean("isNormal");

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
