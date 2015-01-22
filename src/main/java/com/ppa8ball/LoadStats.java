package com.ppa8ball;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.ppa8ball.schedule.Load;
import com.ppa8ball.schedule.Match;
import com.ppa8ball.schedule.Schedule;
import com.ppa8ball.schedule.Week;
import com.ppa8ball.stats.PlayerStat;
import com.ppa8ball.stats.Stats;
import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.service.PlayerService;
import com.ppa8ball.stats.service.PlayerServiceImpl;

//import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Servlet implementation class LoadStats
 */
public class LoadStats extends HttpServlet
{
	private static final long serialVersionUID = 1L;

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
		dropDatabase();

		response.setContentType("text/plain");

		PrintWriter writer = response.getWriter();
		// List<TeamStat> teams =
		// OfyService.myOfy().load().type(TeamStat.class).order("number").list();
		//
		// List<PlayerStat> playerStats =
		// OfyService.myOfy().load().type(PlayerStat.class).order("teamNumber").list();
		//
		// for (TeamStat teamStat : teams)
		// {
		// writer.println("Team Id:" + teamStat.id);
		//
		// writer.println("Team name:" + teamStat.name);
		//
		// writer.println("Team Number: " + teamStat.number);
		// }

		// TeamStat y =
		// OfyService.myOfy().load().type(TeamStat.class).id(5348024557502464L).now();

		Schedule schedule = null;

		// if (teams == null || teams.isEmpty())

		Load l = new Load();

		schedule = l.LoadFromExcel();

		OfyService.myOfy().save().entities(schedule.weeks).now();

		for (Week week : schedule.weeks)
		{
			List<Match> matches = week.getMatches();

			OfyService.myOfy().save().entities(matches).now();

		}

		Stats s = new Stats();

		s.load();

		List<TeamStat> teams = s.getTeams();

		OfyService.myOfy().save().entities(teams).now();

		List<PlayerStat> playerStats = s.getPlayerStats();

		PlayerService playersService = new PlayerServiceImpl();

		playersService.Save(playerStats);

		if (schedule != null)
			for (Week w : schedule.weeks)
			{
				writer.println(w.toString());

				for (Match match : w.getMatches())
				{
					writer.println(match);
				}
			}

		//

		for (TeamStat team : teams)
		{
			writer.println(team.toString());
		}

		for (PlayerStat playerStat : playerStats)
		{
			writer.println(playerStat.toString());
		}

		writer.flush();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

	private void dropDatabase()
	{
		deleteAll(TeamStat.class);
		deleteAll(PlayerStat.class);
		deleteAll(Week.class);
		deleteAll(Match.class);
	}

	private <T> void deleteAll(Class<T> c)
	{
		// You can query for just keys, which will return Key objects much more
		// efficiently than fetching whole objects
		Iterable<Key<T>> allKeys = OfyService.myOfy().load().type(c).keys();

		// Useful for deleting items
		OfyService.myOfy().delete().keys(allKeys);
	}
}
