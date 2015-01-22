package com.ppa8ball.schedule.service;

import com.googlecode.objectify.Key;
import com.ppa8ball.OfyService;
import com.ppa8ball.schedule.Match;

public class MatchServiceImpl implements MatchService
{
	public void Save(Match match)
	{
		OfyService.myOfy().save().entity(match).now();
	}

	public void Save(Iterable<Match> matches)
	{
		for (Match match : matches)
		{
			Save(match);
		}
	}

	public void DeleteAll()
	{
		Iterable<Key<Match>> allKeys = OfyService.myOfy().load().type(Match.class).keys();

		// Useful for deleting items
		OfyService.myOfy().delete().keys(allKeys);
	}

	public Iterable<Match> GetByWeek(int week)
	{
		return OfyService.myOfy().load().type(Match.class).filter("week", week).list();
	}
	
	public Match GetByTeam(int week, int teamNumber)
	{
		Iterable<Match> matches = GetByWeek(week);
		
		for (Match match : matches)
		{
			if (match.homeTeam == teamNumber || match.awayTeam == teamNumber)
				return match;
		}
		
		return null;
	}
}
