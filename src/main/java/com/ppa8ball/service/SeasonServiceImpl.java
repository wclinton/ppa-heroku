package com.ppa8ball.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

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
		session.saveOrUpdate(season);
	}

	@Override
	public Season GetCurrent()
	{
		Criteria cr = session.createCriteria(Season.class);
		List<Season> seasons = cr.list();
		Collections.sort(seasons, Collections.reverseOrder());

		return seasons.get(0);
	}

	@Override
	public Season Get(long id)
	{
		return (Season) session.get(Season.class, id);
	}
}
