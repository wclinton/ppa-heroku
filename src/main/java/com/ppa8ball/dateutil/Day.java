package com.ppa8ball.dateutil;

import java.sql.Date;

public class Day
{
	private Date date;

	public static Day Today()
	{
		return new Day(new java.util.Date());
	}

	public Day(java.util.Date date)
	{
		this.date = new Date(date.getYear(), date.getMonth(), date.getDay());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}
	
	public boolean beforeOrOn(Day otherDay)
	{
		return this.equals(otherDay) || this.before(otherDay);
	}

	public boolean before(Day otherDay)
	{
		return date.before(otherDay.date);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (date == null)
		{
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

}
