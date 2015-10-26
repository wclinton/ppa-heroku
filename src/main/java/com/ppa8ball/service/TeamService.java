package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Session;

import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;

public interface TeamService
{
	public void Save(List<Team> teams);
	public void Save(Team team);
	
	public Team Get(int id);
	public List<Team> GetBySeason(Season season);
	public List<Team> GetNormalBySeason(Season season);
	public Team GetSpareBySeason(Season season);
	public Team GetByNumber(Session session, long seasonId, int number);
}
