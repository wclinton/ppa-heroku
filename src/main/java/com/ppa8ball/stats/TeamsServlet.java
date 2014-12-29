package com.ppa8ball.stats;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.repackaged.com.google.gson.Gson;
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
		TeamService teamService = new TeamsImpl();

		TeamsStat teams = teamService.GetAll();
		
		Gson gson = new Gson();
		String json = gson.toJson(teams);
		
		response.setContentType("text/javascript");
		
		PrintWriter writer = response.getWriter();
		
		writer.write(json);

		//create the JSON string, I suggest using some framework.

		writer.flush();
		writer.close();
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
