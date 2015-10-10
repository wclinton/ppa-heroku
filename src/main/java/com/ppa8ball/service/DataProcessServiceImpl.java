package com.ppa8ball.service;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.ppa8ball.dataloader.Stats;
import com.ppa8ball.models.Player;
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
		
		//Load all the teams for the for the season
		List<Team> teams = Stats.LoadTeams(season);
		TeamService teamService = new TeamServiceImpl(session);
		teamService.Save(teams);
		
        // Load all the Year weeks into the season.
		List<Week> weeks = new Load(session, season).LoadFromExcel();
		season.getWeeks().addAll(weeks);
		
		seasonService.Save(season);

		MatchService matchService = new MatchServiceImpl(session);
		for (Week week : weeks)
		{
			matchService.Save(week.getMatches());
		}
		
		seasonService.Save(season);
		
		//get all the players and the stats
		PlayerService playerService = new PlayerServiceImpl(session);
		
		int lastStatWeek = Stats.GetLatestStatWeek(season);
		
		WeekService weekService = new WeekServiceImpl(session);
		Week lastweek = weekService.getWeekbyNumber(season, lastStatWeek);
		lastweek.setHasStats(true);
		weekService.Save(lastweek);
		
		
		if (lastStatWeek != -1)
		{
		    weekService = new WeekServiceImpl(session);
			Week lastWeek = weekService.getWeekbyNumber(season,lastStatWeek);
			season.setLastStatWeek(lastWeek);
			seasonService.Save(season);
		}
		
		
		Map<Player,Integer> playerMap = Stats.LoadPlayers(season);
		
		for (Map.Entry<Player, Integer> entry : playerMap.entrySet()) 
		{
		   Player p = entry.getKey();
		   
		   int teamNo = entry.getValue();
		   Team t = teamService.GetByNumber(season.getId(),teamNo);
		   
		   Player existingPlayer = playerService.GetByName(p.getFirstName(), p.getLastName());
		   
		   if (existingPlayer != null)
		   {
			   existingPlayer.getStats().addAll(p.getStats());
			   existingPlayer.getTeams().add(t);
			   playerService.Save(existingPlayer);
			   
			   t.getPlayers().add(existingPlayer);			  
		   }
		   else
		   {   
			   p.getTeams().add(t);
			   t.getPlayers().add(p);
		   }
		   teamService.Save(t);
		}
		session.close();
	}

	private void clearDatabases(Session session)
	{
		Session sess = HibernateUtil.getSessionFactory().openSession();
		String[] tables =
		{ "team","season","match", "stat","player", "team_player", "week" };

		for (String table : tables)
		{
			try
			{
				sess.beginTransaction();
				sess.createSQLQuery("TRUNCATE TABLE " + table + " CASCADE").executeUpdate();
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