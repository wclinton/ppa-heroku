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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ppa8ball.stats.Gender;

@Entity
@Table
public class Player
{
	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;
	private String fullName;

	@Enumerated(EnumType.ORDINAL)
	private Gender gender;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn (name = "PLAYER_ID")
	private List<Stat> stats;
	
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "TEAM_ID", nullable = false)
//	private Team team;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "players")
	private List<Team> teams;
	
	
	
	public Player()
	{
	}

	public Player(String firstName, String lastName, String fullName, Gender gender)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.gender = gender;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFullName()
	{
		return fullName;
	}

	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	public Gender getGender()
	{
		return gender;
	}

	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	public List<Stat> getStats()
	{
		if (stats == null)
			stats = new ArrayList<Stat>();

		return stats;
	}

	public void setStats(List<Stat> stats)
	{
		this.stats = stats;
	}

	@Override
	public String toString()
	{
		return "Player: " + fullName;
	}
}
