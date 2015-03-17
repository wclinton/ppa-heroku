package com.ppa8ball.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.ppa8ball.dataloader.Stats;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
import com.ppa8ball.schedule.Load;
import com.ppa8ball.service.MatchService;
import com.ppa8ball.service.MatchServiceImpl;
import com.ppa8ball.service.PlayerService;
import com.ppa8ball.service.PlayerServiceImpl;
import com.ppa8ball.service.SeasonService;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.service.WeekService;
import com.ppa8ball.service.WeekServiceImpl;
import com.ppa8ball.util.HibernateUtil;

//import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Servlet implementation class LoadStats
 */
public class LoadStatsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final String seasonYear = "2014-2015";

	// /**
	// * @see HttpServlet#HttpServlet()
	// */
	// public LoadStats() {
	// super();
	// // TODO Auto-generated constructor stub
	// }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Session session = HibernateUtil.getSessionFactory().openSession();

		clearDatabases(session);

		// Create a season record.
		Season season = new Season(seasonYear);

		SeasonService seasonService = new SeasonServiceImpl(session);
		seasonService.Save(season);

		// Load all the teams and players
		Stats stats = new Stats(season);
		List<Team> teams = stats.loadTeams();

		PlayerService playerService = new PlayerServiceImpl(session);

		for (Team team : teams)
		{
			playerService.Save(team.getPlayers());
		}

		TeamService teamService = new TeamServiceImpl(session);
		teamService.Save(teams);

		// Load all the Year weeks into the season.
		List<Week> weeks = new Load(session, season).LoadFromExcel();

		// MatchService matchService = new MatchServiceImpl(session);
		//
		// for (Week week : weeks)
		// {
		// matchService.Save(week.getMatches());
		// }

		WeekService weekService = new WeekServiceImpl(session);
		weekService.Save(weeks);

		MatchService matchService = new MatchServiceImpl(session);

		for (Week week : weeks)
		{
			matchService.Save(week.getMatches());
		}

		season.getWeeks().addAll(weeks);
		seasonService.Save(season);

		// Load the players and stats

		// List<Player> players = stats.loadPlayersAndStats();
		//
		// PlayerService playerService = new
		// com.ppa8ball.service.PlayerServiceImpl(session);
		// playerService.Save(players);
		//

		List<Team> savedTeams = teamService.GetBySeason(season);
		response.setContentType("text/plain");

		PrintWriter writer = response.getWriter();

		for (Team team : savedTeams)
		{
			writer.println(team);
		}

		// Week savedWeek = weekService.getWeekbyNumber(1);

		writer.flush();

		// Connection connection = DbDriver.getConnection();
		// dropDatabase(connection);
		//
		// response.setContentType("text/plain");
		//

		//
		// Schedule schedule = null;
		//
		// Load l = new Load();
		//
		// schedule = l.LoadFromExcel();
		//
		// WeekService weekService = new WeekServiceImpl(connection);
		//
		// weekService.Save(schedule.weeks);
		//
		// Stats s = new Stats();
		//
		// s.load();
		//
		// List<TeamStat> teams = s.getTeams();
		//
		// new TeamsImpl(connection).Save(teams);
		//
		// List<PlayerStat> playerStats = s.getPlayerStats();
		//
		// PlayerService playersService = new PlayerServiceImpl(connection);
		//
		// playersService.Save(playerStats);
		//
		// for (TeamStat team : teams)
		// {
		// writer.println(team);
		// }
		//
		// for (PlayerStat playerStat : playerStats)
		// {
		// writer.println(playerStat);
		// }
		//
		// writer.flush();
		//
		// try
		// {
		// connection.close();
		// } catch (SQLException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

	private void clearDatabases(Session session)
	{
		String[] tables =
		{ "match", "stat", "player", "season", "team", "teamplayer", "teamroster", "week" };

		for (String table : tables)
		{
			session.createSQLQuery("truncate table " + table + " cascade").executeUpdate();
		}
	}
}
