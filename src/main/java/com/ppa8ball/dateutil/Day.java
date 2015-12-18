package com.ppa8ball.dateutil;

import java.time.*;

public class Day
{
	private LocalDate date;
	
	public static Day TodayInPST()
	{
		ZonedDateTime today =  ZonedDateTime.now(ZoneId.of("America/Vancouver"));
		return new Day(today.toLocalDate());
	}

	public Day(LocalDate date)
	{
		this.date = date;
	}
	
	public Day(java.util.Date date)
	{
		this.date = LocalDate.of(date.getYear() + 1900, date.getMonth()+1, date.getDate());
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
		return date.isBefore(otherDay.date);
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
