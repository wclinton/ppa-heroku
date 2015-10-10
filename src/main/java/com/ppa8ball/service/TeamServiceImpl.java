package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamType;

public class TeamServiceImpl implements TeamService
{

	private Session session;

	public TeamServiceImpl(Session session)
	{
		super();
		this.session = session;
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
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", season.getId()));

		@SuppressWarnings("unchecked")
		List<Team> list = cr.list();
		return list;
	}
	
	@Override
	public List<Team> GetNormalBySeason(Season season)
	{
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", season.getId()));
		cr.add(Restrictions.eq("type", TeamType.Normal));

		@SuppressWarnings("unchecked")
		List<Team> list = cr.list();
		return list;
	}

	@Override
	public Team GetSpareBySeason(Season season)
	{
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", season.getId()));
		cr.add(Restrictions.eq("type", TeamType.Spare));

		return (Team) cr.uniqueResult();
	}
	
	
	@Override
	public Team GetByNumber(long seasonId, int number)
	{
		Criteria cr = session.createCriteria(Team.class);

		cr.add(Restrictions.eq("season.id", seasonId));
		cr.add(Restrictions.eq("number", number));

		return (Team) cr.uniqueResult();
	}
	
	private void Store(Team team)
	{
		session.beginTransaction();
		session.saveOrUpdate(team);
		session.getTransaction().commit();
	}
}
