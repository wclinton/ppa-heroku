package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;

public class WeekServiceImpl implements WeekService
{
	
	private final Session session;

	public WeekServiceImpl(Session session)
	{
		super();
		this.session = session;
	}

	@Override
	public void Save(List<Week> weeks)
	{
		for (Week week : weeks)
		{
			Save(week);
		}
	}

	@Override
	public void Save(Week week)
	{
		session.beginTransaction();
		session.saveOrUpdate(week);
		session.getTransaction().commit();
	}

	@Override
	public Week getWeekbyNumber(int number)
	{
		Criteria cr = session.createCriteria(Week.class);

		cr.add(Restrictions.eq("number", number));

		return (Week) cr.uniqueResult();
	}

	@Override
	public List<Week> getAll()
	{
		Criteria cr = session.createCriteria(Week.class);
		
		return cr.list();
	}

}
