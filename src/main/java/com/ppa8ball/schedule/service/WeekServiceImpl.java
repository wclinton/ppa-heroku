package com.ppa8ball.schedule.service;

import java.util.Collections;
import java.util.List;

import com.googlecode.objectify.Key;
import com.ppa8ball.OfyService;
import com.ppa8ball.schedule.Match;
import com.ppa8ball.schedule.Week;

public class WeekServiceImpl implements WeekService
{

	public List<Week> GetAll()
	{
		List<Week> weeks = OfyService.myOfy().load().type(Week.class).list();

		Collections.sort(weeks);
		return weeks;
	}

	public void Save(Week week)
	{
		OfyService.myOfy().save().entity(week).now();

		List<Match> matches = week.getMatches();

		if (matches != null)
		{
			// Save the matches too ..
			MatchService service = new MatchServiceImpl();
			service.Save(matches);
		}
	}

	public void Save(Iterable<Week> weeks)
	{
		for (Week week : weeks)
		{
			Save(week);
		}
	}

	public void DeleteAll()
	{
		Iterable<Key<Week>> allKeys = OfyService.myOfy().load().type(Week.class).keys();

		// Useful for deleting items
		OfyService.myOfy().delete().keys(allKeys);

		MatchService service = new MatchServiceImpl();
		service.DeleteAll();
	}
}
