package com.ppa8ball.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table

public class Team
{
	@GeneratedValue
	@Id
	private Long id;
	private String name;
	private int number;

	@Enumerated(EnumType.ORDINAL)
	private TeamType type;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn
	private Season season;
	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@JoinColumn (name = "TEAM_ID")
//	List<Player> players;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "team_player", 
	joinColumns = { 
		@JoinColumn(name = "TEAM_ID", nullable = false, updatable = false) }, 
		inverseJoinColumns = { @JoinColumn(name = "PLAYER_ID", 
				nullable = false, updatable = false) })
	private List<Player> players;

	public Team()
	{
	}

	public Team(Season season, String name, int number)
	{
		this(season, name, number, TeamType.Normal);
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public Team(Season season, String name, int number, TeamType type)
	{
		this.season = season;
		this.name = name;
		this.number = number;
		this.type = type;
	}

	public TeamType getType()
	{
		return type;
	}

	public void setType(TeamType type)
	{
		this.type = type;
	}

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

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		String s =  "Season:" + season.getDescription() + " Team:" + number + " " + name;
		
		for (Player player : getPlayers())
		{
			s += "\n" + player;
		}
		
		return s;
	}

	public List<Player> getPlayers()
	{
		if (players == null)
			players = new ArrayList<Player>();
		return players;
	}

	public void setPlayers(List<Player> players)
	{
		this.players = players;
	}
}