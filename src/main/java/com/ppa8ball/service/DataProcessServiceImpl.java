package com.ppa8ball.service;

import java.util.List;

import org.hibernate.Session;

import com.ppa8ball.dataloader.Stats;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
import com.ppa8ball.schedule.Load;
import com.ppa8ball.util.HibernateUtil;

public class DataProcessServiceImpl implements DataProcessService
{

	@Override
	public void Process(Season season)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		clearDatabases(session);

		SeasonService seasonService = new SeasonServiceImpl(session);
		seasonService.Save(season);

		// Load all the season teams, players and stats 
		//Stats stats = new Stats(season);
		List<Team> teams = Stats.LoadSeasonStats(season);

		PlayerService playerService = new PlayerServiceImpl(session);
		for (Team team : teams)
		{
			playerService.Save(team.getPlayers());
		}

		TeamService teamService = new TeamServiceImpl(session);
		teamService.Save(teams);

		// Load all the Year weeks into the season.
		List<Week> weeks = new Load(session, season).LoadFromExcel();

		WeekService weekService = new WeekServiceImpl(session);
		weekService.Save(weeks);

		MatchService matchService = new MatchServiceImpl(session);
		for (Week week : weeks)
		{
			matchService.Save(week.getMatches());
		}

		season.getWeeks().addAll(weeks);
		seasonService.Save(season);
		
		session.close();
	}

	private void clearDatabases(Session session)
	{
		Session sess = HibernateUtil.getSessionFactory().openSession();
		String[] tables =
		{ "match", "stat", "player", "team", "teamplayer", "teamroster", "week", "season" };

		for (String table : tables)
		{
			try
			{
				sess.beginTransaction();
				sess.createSQLQuery("delete from  " + table).executeUpdate();
				sess.getTransaction().commit();
			} catch (Exception e)
			{
				int i = 0;
				i++;
				// swallow the exception.
			}
		}
	}
}
