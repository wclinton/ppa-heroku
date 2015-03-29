package com.ppa8ball.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class TeamRoster
{
	@Id
	@GeneratedValue
	private Long id;

	private boolean isHome;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Player player1;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Player player2;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Player player3;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Player player4;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Player player5;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private Team team;

	public TeamRoster()
	{

	}

	public TeamRoster(Team team, boolean isHome, List<Player> players)
	{
		this.team = team;
		this.isHome = isHome;

		setPlayers(players);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Team getTeam()
	{
		return team;
	}

	public void setTeam(Team team)
	{
		this.team = team;
	}

	public Boolean getIsHome()
	{
		return isHome;
	}

	public void setIsHome(Boolean isHome)
	{
		this.isHome = isHome;
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

	public void setPlayers(List<Player> players)
	{
		int index = 0;

		this.player1 = players.get(index++);
		this.player2 = players.get(index++);
		this.player3 = players.get(index++);
		this.player4 = players.get(index++);
		this.player5 = players.get(index++);
	}
}
