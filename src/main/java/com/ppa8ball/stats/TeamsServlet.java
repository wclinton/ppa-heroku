package com.ppa8ball.stats;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ppa8ball.db.DbDriver;
import com.ppa8ball.stats.service.TeamService;
import com.ppa8ball.stats.service.TeamsImpl;

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
		Connection connection = DbDriver.getConnection();
		TeamService teamService = new TeamsImpl(connection);

		TeamsStat teams = teamService.GetAll();
		
		Gson gson = new Gson();
		String json = gson.toJson(teams);
		
		response.setContentType("text/javascript");
		
		PrintWriter writer = response.getWriter();
		
		writer.write(json);

		//create the JSON string, I suggest using some framework.

		writer.flush();
		writer.close();
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}

}
