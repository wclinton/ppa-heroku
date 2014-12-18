package com.ppa8ball;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.ppa8ball.stats.PlayerStat;
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

		request.getParameter("drop");

		boolean drop = Boolean.valueOf(request.getParameter("drop") != null);

		if (drop)
		{
			dropDatabase();
		}
		response.setContentType("text/plain");

		PrintWriter writer = response.getWriter();
		List<TeamStat> teams = OfyService.myOfy().load().type(TeamStat.class).order("number").list();
		
		List <PlayerStat> playerStats = OfyService.myOfy().load().type(PlayerStat.class).order("teamNumber").list();
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

		if (teams == null || teams.isEmpty())
		{
			Stats s = new Stats();

			s.load();

			teams = s.getTeams();

			OfyService.myOfy().save().entities(teams).now();
			
			 playerStats = s.getPlayerStats();
			
			OfyService.myOfy().save().entities(playerStats).now();
		}
		
		
		//
		
		for (TeamStat x : teams)
		{
			writer.println("Team Id:" + x.id);

			writer.println("Team name:" + x.name);

			writer.println("Team Number: " + x.number);
		}
		
		for (PlayerStat playerStat : playerStats)
		{
			writer.println("Player Id:" + playerStat.id);

			writer.println("Player Team Number:" + playerStat.teamNumber);

			writer.println("Player Full Name: " + playerStat.fullName);
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
	}

	private <T> void deleteAll(Class<T> c)
	{
		// You can query for just keys, which will return Key objects much more
		// efficiently than fetching whole objects
		Iterable<Key<T>> allKeys = OfyService.myOfy().load().type(c).keys();

		// Useful for deleting items
		OfyService.myOfy().delete().keys(allKeys);
	}
	
//	private String show(TeamStat teamStat)
//	{
//		
//	}

}
