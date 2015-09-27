package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.ppa8ball.models.Season;

public class SeasonServiceImpl implements SeasonService
{
	private Session session;
	
	public SeasonServiceImpl(Session session)
	{
		this.session = session;
	}

	@Override
	public void Save(Season season)
	{
		session.beginTransaction();
		session.saveOrUpdate(season);
		session.getTransaction().commit();
	}

	@Override
	public Season GetCurrent()
	{
		Criteria cr = session.createCriteria(Season.class);
		cr.addOrder(Order.asc("startYear"));

		@SuppressWarnings("unchecked")
		List<Season>  season = cr.list();
		
		return season.get(0);
	}
	
}
