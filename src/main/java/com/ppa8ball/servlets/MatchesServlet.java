package com.ppa8ball.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.ppa8ball.models.Match;
import com.ppa8ball.service.MatchService;
import com.ppa8ball.service.MatchServiceImpl;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.MatchView;

/**
 * Servlet implementation class MatchesServlet
 */
public class MatchesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MatchesServlet()
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
		 final String weekNumberString = request.getParameter("week");
		 final String teamNumberString = request.getParameter("teamNumber");
		
		 final int week = Integer.parseInt(weekNumberString);
		 final int teamNumber = Integer.parseInt(teamNumberString);
		 
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 
		 MatchService service  = new MatchServiceImpl(session);
		 
		 Match match = service.getMatchByWeekTeam(week, teamNumber);
		 
		 JsonHelper.ReturnJson(response, (Object) new MatchView(match));
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
