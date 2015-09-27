package com.ppa8ball.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table

public class Season
{
	
	private Long id;
	private int startYear;
	private int endYear;
	private String description;
	private List<Week> weeks;
	
	//Default constructor
	public Season()
	{
	}
	
	public Season(int startYear)
	{
		this(startYear, null);
	}

	public Season(int startYear, List<Week> weeks)
	{
		super();
		
		this.startYear = startYear;
	
		if (weeks == null)
			this.weeks = new ArrayList<Week>();
		else
			this.weeks = weeks;
	}
	@Id
	@GeneratedValue
	@Column
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	@Column
	public int getStartYear()
	{
		return startYear;
	}
	
	public void setStartYear(int startYear)
	{
		this.startYear = startYear;
	}

	@Column
	public int getEndYear()
	{
		if (endYear == 0)
		{
			endYear = startYear+1;
		}
		return endYear;
	}

	public void setEndYear(int endYear)
	{
		this.endYear = endYear;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn (name = "SEASON_ID")
	public List<Week> getWeeks()
	{
		return weeks;
	}

	public void setWeeks(List<Week> weeks)
	{
		this.weeks = weeks;
	}

	@Column
	public String getDescription()
	{
		if (description == null)
			description =  getStartYear() + "-" + getEndYear();
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