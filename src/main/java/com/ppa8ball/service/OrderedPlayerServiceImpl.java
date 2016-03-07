package com.ppa8ball.service;

import org.hibernate.Session;

import com.ppa8ball.models.OrderedPlayer;

public class OrderedPlayerServiceImpl implements OrderedPlayerService {

	private Session session;
	
	public OrderedPlayerServiceImpl(Session session)
	{
		this.session = session;
	}
	
	@Override
	public void Save(OrderedPlayer orderedPlayer) {
		
		session.saveOrUpdate(orderedPlayer);
	}

}
