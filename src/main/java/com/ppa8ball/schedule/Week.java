package com.ppa8ball.schedule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Week implements Comparable<Week>
{
	@Id public Long id;
	@Index public String season;
	@Index public int number;
	public Date date;
	@Ignore private List<Match> matches;
	
	
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
		return "Week:"+number + " " + formattedDate;
	}
	
	
	public int CcompareTo(Week w)
	{
		if (this.number < w.number)
			return -1;
		else return 1;
	}
}
