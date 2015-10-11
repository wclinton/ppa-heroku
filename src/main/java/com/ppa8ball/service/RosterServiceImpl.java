package com.ppa8ball.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Roster;
import com.ppa8ball.models.Team;

public class RosterServiceImpl implements RosterService
{
	private final Session session;

	public RosterServiceImpl(Session session)
	{
		this.session = session;
	}

	@Override
	public Roster getByTeam(long seasonId, long teamId)
	{
		Criteria cr = session.createCriteria(Roster.class);

		cr.add(Restrictions.eq("season.id", seasonId));
		cr.add(Restrictions.eq("team.id", teamId));

		return (Roster) cr.uniqueResult();
	}

	@Override
	public void Save(Roster roster)
	{
		session.beginTransaction();
		session.saveOrUpdate(roster);
		session.getTransaction().commit();
	}
}
