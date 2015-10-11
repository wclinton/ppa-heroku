package com.ppa8ball.models;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

public class Roster
{
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Season getSeason()
	{
		return season;
	}

	public void setSeason(Season season)
	{
		this.season = season;
	}

	public Team getTeam()
	{
		return team;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public Player getPlayer1()
	{
		return player1;
	}

	public void setPlayer1(Player player1)
	{
		this.player1 = player1;
	}

	public Player getPlayer2()
	{
		return player2;
	}

	public void setPlayer2(Player player2)
	{
		this.player2 = player2;
	}

	public Player getPlayer3()
	{
		return player3;
	}

	public void setPlayer3(Player player3)
	{
		this.player3 = player3;
	}

	public Player getPlayer4()
	{
		return player4;
	}

	public void setPlayer4(Player player4)
	{
		this.player4 = player4;
	}

	public Player getPlayer5()
	{
		return player5;
	}

	public void setPlayer5(Player player5)
	{
		this.player5 = player5;
	}

	public boolean isHome()
	{
		return isHome;
	}

	public void setHome(boolean isHome)
	{
		this.isHome = isHome;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn
	private Season season;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn
	private Team team;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Player player1;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Player player2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Player player3;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Player player4;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Player player5;
	
	private boolean isHome;
}
