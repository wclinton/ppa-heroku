package com.ppa8ball.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.service.SeasonService;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.TeamsView;

/**
 * Servlet implementation class TeamsServlet
 */
public class TeamsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TeamsServlet()
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
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		//Get the current sesson 
		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();
		
		TeamService teamService = new TeamServiceImpl(session);
		List<Team> teams = teamService.GetNormalBySeason(currentSeason);
		
		TeamsView teamView = new TeamsView(teams);
		
		Gson gson = new Gson();
		String json = gson.toJson(teamView);
		
		response.setContentType("text/javascript");
		
		PrintWriter writer = response.getWriter();
		
		writer.write(json);

		//create the JSON string, I suggest using some framework.

		writer.flush();
		writer.close();
		
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
