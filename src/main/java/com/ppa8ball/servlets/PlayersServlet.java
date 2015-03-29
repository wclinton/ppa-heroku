package com.ppa8ball.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.service.SeasonService;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.PlayerView;
import com.ppa8ball.viewmodel.PlayersView;

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
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		SeasonService seasonService = new SeasonServiceImpl(session);
		
		Season season = seasonService.GetCurrent();
		TeamService teamService = new TeamServiceImpl(session);

		final String sparesString = request.getParameter("spares");
		
		Team team;

		if (sparesString != null)
			getSpares = Boolean.parseBoolean(sparesString);
		else
			getSpares = false;
		
		if (getSpares)
		{
			team = teamService.GetSpareBySeason(season);
		}
		else
		{
			final String teamNumberString = request.getParameter("teamNumber");
			team = teamService.GetByNumber(season, Integer.parseInt(teamNumberString));
		}
		
		
		
		PlayersView players = new PlayersView();
		
		for (Player player : team.getPlayers())
		{
			players.getPlayers().add(new PlayerView(player));
		}
		
		JsonHelper.ReturnJson(response, (Object) players);
		
		session.close();
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
