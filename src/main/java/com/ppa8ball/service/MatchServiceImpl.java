package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Match;

public class MatchServiceImpl implements MatchService
{
	private Session session;

	public MatchServiceImpl(Session session)
	{
		super();
		this.session = session;
	}

	@Override
	public Match getMatchByWeekTeam(long seasonId, int weekNumber, int teamNumber)
	{
		Criterion week = Restrictions.eq("W.number", weekNumber);
		Criterion awayTeam = Restrictions.eq("A.number", teamNumber);
		Criterion homeTeam = Restrictions.eq("H.number", teamNumber);
		
		Criterion team = Restrictions.or(awayTeam, homeTeam);
		Criterion season = Restrictions.eq("S.id",seasonId);
		
		//Criteria restriction = Restrictions.and(week,team);
		
		Criteria criteria = session.createCriteria(Match.class)
				.createAlias("week", "W")
				.createAlias("away", "A")
				.createAlias("home", "H")
				.createAlias("season", "S")
				.add(season)
				.add(Restrictions.and(week, team));
				
		return (Match) criteria.uniqueResult();
	}

	@Override
	public void Save(List<Match> matches)
	{
		for (Match match : matches)
		{
			Save(match);
		}
	}

	@Override
	public void Save(Match match)
	{
		session.beginTransaction();
		session.saveOrUpdate(match);
		session.getTransaction().commit();
	}

}
