package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Team;

public class PlayerServiceImpl implements PlayerService
{
	private Session session;
	
	public PlayerServiceImpl(Session session)
	{
		super();
		this.session = session;
	}

	@Override
	public void Save(Player player)
	{
		session.saveOrUpdate(player);
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
		Criteria cr = session.createCriteria(Team.class);
		cr.add(Restrictions.eq("team.id", team.getId()));
		return cr.list();
	}

	@Override
	public List<Player> GetSpare()
	{
		return null;
	}

	@Override
	public Player Get(long id)
	{
		 return  (Player) session.get(Player.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Player> Get()
	{
		return (List<Player>) session.createCriteria(Player.class).list();
	}
	
	@Override
	public Player GetByName(String firstName, String lastName)
	{
		Criteria cr = session.createCriteria(Player.class);
		cr.add(Restrictions.eq("firstName", firstName));
		cr.add(Restrictions.eq("lastName", lastName));
		
		cr.setMaxResults(1);
		
		return (Player) cr.uniqueResult();
	}
}