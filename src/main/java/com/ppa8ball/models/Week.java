package com.ppa8ball.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Week
{
	@Id
	@GeneratedValue
	private Long id;
	private int number;
	private Date date;
	private Boolean hasStats = false;
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy="week")
	private List<Match> matches;
	
	public Week()
	{
	}
	
	public Week(int number, Date date)
	{
		this(number, date, null);
	}

	public Week(int number, Date date, List<Match> matches)
	{
		super();
		this.number = number;
		this.date = date;
		if (matches == null)
			matches = new ArrayList<Match>();
		else
		 this.matches = matches;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public List<Match> getMatches()
	{
		if (matches == null)
			matches = new ArrayList<Match>();
		return matches;
	}

	public void setMatches(List<Match> matches)
	{
		this.matches = matches;
	}
	
	public Boolean getHasStats()
	{
		return hasStats;
	}

	public void setHasStats(Boolean hasStats)
	{
		this.hasStats = hasStats;
	}
}
