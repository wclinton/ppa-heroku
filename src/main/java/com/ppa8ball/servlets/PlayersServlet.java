package com.ppa8ball.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ppa8ball.stats.PlayersStat;
import com.ppa8ball.stats.service.PlayerService;
import com.ppa8ball.stats.service.PlayerServiceImpl;

/**
 * Servlet implementation class PlayersServlet
 */
public class PlayersServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlayersServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		final boolean getSpares;
		final int teamNumber;

		final String sparesString = request.getParameter("spares");
		final String teamNumberString = request.getParameter("teamNumber");

		if (sparesString != null)
			getSpares = Boolean.parseBoolean(sparesString);
		else
			getSpares = false;
		
		PlayerService service = new PlayerServiceImpl();
		final PlayersStat players;
		
		if (getSpares)
		{
			teamNumber = 11;
			players = service.GetPlayerByTeam(teamNumber);
		}
		else if (teamNumberString != null)
		{
			
			teamNumber = Integer.parseInt(teamNumberString);
			players = service.GetPlayerByTeam(teamNumber);
		}
		else
		{
			players = service.GetAllPlayers();
		}

		JsonHelper.ReturnJson(response, (Object) players);
	}
	
	
	//PlayersStat getPlayersByTeam(int team)

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}
}
