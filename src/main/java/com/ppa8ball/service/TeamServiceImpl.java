package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamType;

public class TeamServiceImpl implements TeamService
{

	private SessionFactory sessionFactory;

	public TeamServiceImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void Save(List<Team> teams)
	{
		for (Team team : teams)
		{
			Store(team);
		}
	}

	@Override
	public void Save(Team team)
	{
		Store(team);
	}

	@Override
	public Team Get(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Team> GetBySeason(Season season)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", season.getId()));

		@SuppressWarnings("unchecked")
		List<Team> list = cr.list();
		session.getTransaction().commit();
		return list;
	}

	@Override
	public List<Team> GetNormalBySeason(Season season)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", season.getId()));
		cr.add(Restrictions.eq("type", TeamType.Normal));

		@SuppressWarnings("unchecked")
		List<Team> list = cr.list();
		session.getTransaction().commit();
		return list;
	}

	@Override
	public Team GetSpareBySeason(Season season)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Team.class);
		cr.add(Restrictions.eq("season.id", season.getId()));
		cr.add(Restrictions.eq("type", TeamType.Spare));
		Team team = (Team) cr.uniqueResult();
		session.getTransaction().commit();
		return team;
	}

	@Override
	public Team GetByNumber(Session session, long seasonId, int number)
	{
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", seasonId));
		cr.add(Restrictions.eq("number", number));

		Team team = (Team) cr.uniqueResult();

		return team;
	}

	private void Store(Team team)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(team);
		session.getTransaction().commit();
	}
}
