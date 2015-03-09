package com.ppa8ball.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ppa8ball.db.DbDriver;
import com.ppa8ball.schedule.Match;
import com.ppa8ball.schedule.service.MatchService;
import com.ppa8ball.schedule.service.MatchServiceImpl;

/**
 * Servlet implementation class MatchesServlet
 */
public class MatchesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MatchesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final int week;
		final int teamNumber;

		final String weekNumberString = request.getParameter("week");
		final String teamNumberString = request.getParameter("teamNumber");
		
		Connection connection = DbDriver.getConnection();
		week = Integer.parseInt(weekNumberString);
		
		teamNumber = Integer.parseInt(teamNumberString);
		
		
		MatchService service = new MatchServiceImpl(connection);
		
		Match match = service.GetByTeam(week, teamNumber);
		JsonHelper.ReturnJson(response, (Object) match);	
		
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
