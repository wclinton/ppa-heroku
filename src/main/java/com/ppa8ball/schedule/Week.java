package com.ppa8ball.schedule;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Week implements Comparable<Week>
{
	public int id;
	public String season;
	public int number;
	public Date date;
	private List<Match> matches;

	public Week()
	{
		matches = new ArrayList<Match>();
	}

	public Week(String season, int number, Date date)
	{
		this();
		this.season = season;
		this.number = number;
		this.date = date;
	}
	
	public Week(ResultSet rs)
	{
		try
		{
			id = rs.getInt("id");
			season = rs.getString("season");
			number = rs.getInt("number");
			date = rs.getDate("date");
		
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMatches(List<Match> matches)
	{
		this.matches = matches;
	}

	public List<Match> getMatches()
	{
		if (matches == null)
			matches = new ArrayList<Match>();

		return matches;
	}

	public String toString()
	{
		String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
		return "Week:" + number + " " + formattedDate;
	}

	@Override
	public int compareTo(Week w)
	{
		if (this.number < w.number)
			return -1;
		else
			return 1;
	}
}
