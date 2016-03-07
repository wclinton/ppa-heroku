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

public class DataProcessServiceImpl implements DataProcessService
{
	private static final String WEEK_TABLE_NAME = "week";
	private static final String TEAM_PLAYER_TABLE_NAME = "team_player";
	private static final String PLAYER_TABLE_NAME = "player";
	private static final String STAT_TABLE_NAME = "stat";
	private static final String MATCH_TABLE_NAME = "match";
	private static final String SEASON_TABLE_NAME = "season";
	private static final String TEAM_TABLE_NAME = "team";
	private static final String ROSTER_TABLE_NAME = "roster";
	private static final String ORDERED_PLAYER_TABLE_NAME = "orderedplayer";
	
	private Session session;

	public DataProcessServiceImpl(Session session)
	{
		this.session = session;
	}

	@Override
	public void Clear()
	{
		clearDatabases(session);
	}

	@Override
	public void Process(Season season)
	{
		SeasonService seasonService = new SeasonServiceImpl(session);
		seasonService.Save(season);

		// Load all the teams for the for the season
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

		// get all the players and the stats
		PlayerService playerService = new PlayerServiceImpl(session);

		int lastStatWeek = Stats.GetLatestStatWeek(season);

		WeekService weekService = new WeekServiceImpl(session);
		Week lastweek = weekService.getWeekbyNumber(season, lastStatWeek);
		lastweek.setHasStats(true);
		weekService.Save(lastweek);

		if (lastStatWeek != -1)
		{
			weekService = new WeekServiceImpl(session);
			Week lastWeek = weekService.getWeekbyNumber(season, lastStatWeek);
			lastWeek.setSeason(season);
			season.setLastStatWeek(lastWeek);
			seasonService.Save(season);
		}

		Map<Player, Integer> playerMap = Stats.LoadPlayers(season);

		for (Map.Entry<Player, Integer> entry : playerMap.entrySet())
		{
			Player p = entry.getKey();

			int teamNo = entry.getValue();
			Team t = teamService.GetByNumber(season.getId(), teamNo);

			Player existingPlayer = playerService.GetByName(p.getFirstName(), p.getLastName());

			if (existingPlayer != null)
			{
				existingPlayer.getStats().addAll(p.getStats());
				existingPlayer.getTeams().add(t);
				playerService.Save(existingPlayer);

				t.getPlayers().add(existingPlayer);
			} else
			{
				p.getTeams().add(t);
				t.getPlayers().add(p);
			}
			teamService.Save(t);
		}
	}

	private void clearDatabases(Session session)
	{
		String[] tables =
		{ TEAM_TABLE_NAME, SEASON_TABLE_NAME, MATCH_TABLE_NAME, STAT_TABLE_NAME, PLAYER_TABLE_NAME, TEAM_PLAYER_TABLE_NAME, WEEK_TABLE_NAME, ROSTER_TABLE_NAME, ORDERED_PLAYER_TABLE_NAME};

		for (String table : tables)
		{
			try
			{
				session.createSQLQuery("TRUNCATE TABLE " + table + " CASCADE").executeUpdate();
			} catch (Exception e)
			{
				// swallow the exception.
			}
		}
	}
}