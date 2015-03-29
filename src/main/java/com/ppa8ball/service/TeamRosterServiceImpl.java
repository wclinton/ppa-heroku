package com.ppa8ball.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamRoster;

public class TeamRosterServiceImpl implements TeamRosterService
{
	
	private Session session;
	
	public TeamRosterServiceImpl(Session session)
	{
		super();
		this.session = session;
	}

	@Override
	public void Save(TeamRoster teamRoster)
	{
		session.beginTransaction();
		session.saveOrUpdate(teamRoster);
		session.getTransaction().commit();
	}

	@Override
	public TeamRoster GetHomeRoster(Team team)
	{
		return GetRosterByTeam(team, true);
	}
	
	@Override
	public TeamRoster GetAwayRoster(Team team)
	{
		return GetRosterByTeam(team, true);
	}
	
	public TeamRoster GetRosterByTeam(Team team, boolean isHome)
	{
		Criteria cr = session.createCriteria(TeamRoster.class);

		cr.add(Restrictions.eq("team.id", team.getId()));
		cr.add(Restrictions.eq("isHome", isHome));
		
		return (TeamRoster) cr.uniqueResult();
	}
}
