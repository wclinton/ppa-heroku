package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Match;

public class MatchServiceImpl implements MatchService
{
	private SessionFactory sessionFacory;

	public MatchServiceImpl(SessionFactory sessionFacory)
	{
		super();
		this.sessionFacory = sessionFacory;
	}

	@Override
	public Match getMatchByWeekTeam(long seasonId, int weekNumber, int teamNumber)
	{
		Session session = sessionFacory.getCurrentSession();
		session.beginTransaction();
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
				
		Match match = (Match) criteria.uniqueResult();
		
		session.getTransaction().commit();
		return match;
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
		Session session = sessionFacory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(match);
		session.getTransaction().commit();
	}
}
