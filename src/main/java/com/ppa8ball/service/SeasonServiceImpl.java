package com.ppa8ball.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ppa8ball.models.Season;

public class SeasonServiceImpl implements SeasonService
{
	private SessionFactory sessionFactory;

	public SeasonServiceImpl(SessionFactory factory)
	{
		this.sessionFactory = factory;
	}

	@Override
	public void Save(Season season)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(season);
		session.getTransaction().commit();
	}

	@Override
	public Season GetCurrent()
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria cr = session.createCriteria(Season.class);
		List<Season> seasons = cr.list();
		Collections.sort(seasons, Collections.reverseOrder());
		session.getTransaction().commit();
		return seasons.get(0);
	}

	@Override
	public Season Get(long id)
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Season season = (Season) session.get(Season.class, id);
		session.getTransaction().commit();
		return season;
	}
}
