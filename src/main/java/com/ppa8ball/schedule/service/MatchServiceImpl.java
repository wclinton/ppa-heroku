package com.ppa8ball.schedule.service;

import com.googlecode.objectify.Key;
import com.ppa8ball.OfyService;
import com.ppa8ball.schedule.Match;
import com.ppa8ball.schedule.Week;

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
}
