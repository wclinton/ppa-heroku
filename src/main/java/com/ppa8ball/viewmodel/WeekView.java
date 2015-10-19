package com.ppa8ball.viewmodel;

import java.sql.Date;

import com.ppa8ball.models.Week;

public class WeekView
{
	private Long id;
	private int number;
	private Date date;
	
	
	public WeekView()
	{
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

	public WeekView(Week week)
	{
		this.setId(week.getId());
		this.setNumber(week.getNumber());
		this.setDate(week.getDate());
	}
}
