package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Season;
import com.ppa8ball.models.Week;

public class WeekServiceImpl implements WeekService
{
	
	private final SessionFactory sessionFactory;

	public WeekServiceImpl(SessionFactory sessionFactory)
	{
		super();
		this.sessionFactory = sessionFactory;
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
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(week);
		session.getTransaction().commit();
	}

	@Override
	public Week getWeekbyNumber(Season season, int number)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Season.class);
		cr.add(Restrictions.eq("id",season.getId()));
		Season s = (Season) cr.uniqueResult();
		session.getTransaction().commit();
		
		for (Week week : s.getWeeks())
		{
			if (week.getNumber() == number)
				return week;
		}
		return null;
	}

	@Override
	public List<Week> getAll()
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Week.class);
		
		@SuppressWarnings("unchecked")
		List<Week> list = cr.list();
		session.getTransaction().commit();
		return list;
	}
	
	@Override
	public List<Week> getAll(Season season)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Season.class);
		cr.add(Restrictions.eq("id",season.getId()));
		Season s = (Season) cr.uniqueResult();
		session.getTransaction().commit();
		return s.getWeeks();
	}

}
