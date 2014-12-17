package com.ppa8ball;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.ppa8ball.stats.Stats;
import com.ppa8ball.stats.TeamStat;

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
		// TODO Auto-generated method stub
		response.setContentType("text/plain");

		PrintWriter writer = response.getWriter();
		List<TeamStat> teams = OfyService.myOfy().load().type(TeamStat.class).limit(10).list();

		for (TeamStat teamStat : teams)
		{
			writer.println("Team Id:" + teamStat.id);

			writer.println("Team name:" + teamStat.name);

			writer.println("Team Number: " + teamStat.number);
		}

		TeamStat y = OfyService.myOfy().load().type(TeamStat.class).id(5348024557502464L).now();

		Iterable<Key<TeamStat>> allKeys = OfyService.myOfy().load().type(TeamStat.class).keys();

		if (teams == null || teams.size() == 0)
		{

			Stats s = new Stats();

			s.load();

			// final Objectify ofy = ObjectifyService.ofy();

			TeamStat team = s.getTeam(1);

			OfyService.myOfy().save().entity(team).now();

			TeamStat x = OfyService.myOfy().load().type(TeamStat.class).id(team.id).now();

			writer.println("Team Id:" + x.id);

			writer.println("Team name:" + x.name);

			writer.println("Team Number: " + x.number);

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

}
