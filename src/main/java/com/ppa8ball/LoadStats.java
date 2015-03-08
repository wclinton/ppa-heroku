package com.ppa8ball;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ppa8ball.schedule.Load;
import com.ppa8ball.schedule.Match;
import com.ppa8ball.schedule.Schedule;
import com.ppa8ball.schedule.Week;
import com.ppa8ball.schedule.service.WeekService;
import com.ppa8ball.schedule.service.WeekServiceImpl;
import com.ppa8ball.stats.PlayerStat;
import com.ppa8ball.stats.Stats;
import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.service.PlayerService;
import com.ppa8ball.stats.service.PlayerServiceImpl;
import com.ppa8ball.stats.service.TeamsImpl;

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
	
		Schedule schedule = null;

		Load l = new Load();

		schedule = l.LoadFromExcel();
		
		WeekService weekService = new WeekServiceImpl();
		
		weekService.Save(schedule.weeks);
	
		Stats s = new Stats();

		s.load();

		List<TeamStat> teams = s.getTeams();

		new TeamsImpl().Save(teams);

		List<PlayerStat> playerStats = s.getPlayerStats();

		PlayerService playersService = new PlayerServiceImpl();

		playersService.Save(playerStats);

		if (schedule != null)
			for (Week w : schedule.weeks)
			{
				writer.println(w);

				for (Match match : w.getMatches())
				{
					writer.println(match);
				}
			}

		for (TeamStat team : teams)
		{
			writer.println(team);
		}

		for (PlayerStat playerStat : playerStats)
		{
			writer.println(playerStat);
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
		new PlayerServiceImpl().DeleteAll();
		new TeamsImpl().DeleteAll();
		
		//Drop all the weeks;
		
		WeekService weekService = new WeekServiceImpl();
		weekService.DropTable();
		weekService.CreateTable();
		
	}
}
