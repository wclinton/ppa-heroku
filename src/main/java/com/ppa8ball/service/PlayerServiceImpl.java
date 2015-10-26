package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Team;

public class PlayerServiceImpl implements PlayerService
{
	private SessionFactory sessionFactory;

	public PlayerServiceImpl(SessionFactory sessionFactory)
	{
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void Save(Player player)
	{
		Session session = getSession();
		session.saveOrUpdate(player);
		session.getTransaction().commit();
	}

	@Override
	public void Save(List<Player> players)
	{
		for (Player player : players)
		{
			Save(player);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> GetByTeam(Team team)
	{
		Session session = getSession();
		Criteria cr = session.createCriteria(Team.class);
		cr.add(Restrictions.eq("team.id", team.getId()));

		List<Player> players = cr.list();

		session.getTransaction().commit();
		return players;
	}

	@Override
	public List<Player> GetSpare()
	{
		return null;
	}

	@Override
	public Player Get(long id)
	{
		Session session = getSession();

		Player player = (Player) session.get(Player.class, id);

		session.getTransaction().commit();

		return player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> Get()
	{
		Session session = getSession();
		List<Player> players = session.createCriteria(Player.class).list();
		
		session.getTransaction().commit();
		
		return players;
	}

	@Override
	public Player GetByName(String firstName, String lastName)
	{
		Session session = getSession();
		Criteria cr = session.createCriteria(Player.class);
		cr.add(Restrictions.eq("firstName", firstName));
		cr.add(Restrictions.eq("lastName", lastName));

		cr.setMaxResults(1);

		Player player = (Player) cr.uniqueResult();
		session.getTransaction().commit();

		return player;
	}

	private Session getSession()
	{
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		return session;
	}
}