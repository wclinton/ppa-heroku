package com.ppa8ball.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Season
{
	@Id
	@GeneratedValue
	private Long id;
	private String description;
	//private Week lastWeekPlayed;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<Week> weeks;
	
	//Default constructor
	public Season()
	{
	}

	public Season(String description)
	{
		this(description, null);
	}

	public Season(String description, List<Week> weeks)
	{
		super();
		this.description = description;

		if (weeks == null)
			this.weeks = new ArrayList<Week>();
		else
			this.weeks = weeks;
	}

	public List<Week> getWeeks()
	{
		return weeks;
	}

	public void setWeeks(List<Week> weeks)
	{
		this.weeks = weeks;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "Season:"+ description;
	}
}
