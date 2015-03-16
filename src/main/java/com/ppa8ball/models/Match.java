package com.ppa8ball.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Match
{
	@Id
	@GeneratedValue
	private Long id;
	private int number;
	private String table1;
	private String table2;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Team home;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Team away;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Week week;

	public Match()
	{
	}

	public Match(int number, String table1, String table2, Team home, Team away)
	{
		super();
		this.number = number;
		this.table1 = table1;
		this.table2 = table2;
		this.home = home;
		this.away = away;
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

	public String getTable1()
	{
		return table1;
	}

	public void setTable1(String table1)
	{
		this.table1 = table1;
	}

	public String getTable2()
	{
		return table2;
	}

	public void setTable2(String table2)
	{
		this.table2 = table2;
	}

	public Team getHome()
	{
		return home;
	}

	public void setHome(Team home)
	{
		this.home = home;
	}

	public Team getAway()
	{
		return away;
	}

	public void setAway(Team away)
	{
		this.away = away;
	}

	public Week getWeek()
	{
		return week;
	}

	public void setWeek(Week week)
	{
		this.week = week;
	}
}
