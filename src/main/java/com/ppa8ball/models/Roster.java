package com.ppa8ball.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@Table
(uniqueConstraints=
@UniqueConstraint(columnNames = {"ishome","team_id","season_id"})) 

public class Roster
{
	@Id
	@GeneratedValue
	private long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn
	private Season season;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn
	private Team team;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "roster_player", joinColumns =
	{ @JoinColumn(name = "ROSTER_ID", nullable = false, updatable = false) }, inverseJoinColumns =
	{ @JoinColumn(name = "PLAYER_ID", nullable = false, updatable = false) })
	private List<Player> players;
	
	@Column
	private boolean isHome;
	
	public long getId()
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

	
	public void setPlayers(List<Player>  players)
	{
		this.players = players;
	}
	
	
	public List<Player> getPlayers()
	{
		return players;
	}
	
	public boolean isHome()
	{
		return isHome;
	}

	public void setHome(boolean isHome)
	{
		this.isHome = isHome;
	}
}
