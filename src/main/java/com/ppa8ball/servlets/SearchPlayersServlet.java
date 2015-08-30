package com.ppa8ball.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchPlayersServlet
 */
public class SearchPlayersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchPlayersServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//    	response.setContentType("text/plain");
//		String searchName =  request.getParameter("name");
//		
//		List<PlayerStat> players = OfyService.myOfy().load().type(PlayerStat.class).filter("fullName >=", searchName).filter("fullName <", searchName + "\uFFFD").list();
//		PrintWriter writer = response.getWriter();
//		for (PlayerStat playerStat : players)
//		{
//			writer.println("Player Id:" + playerStat.id);
//
//			writer.println("Player Team Number:" + playerStat.teamNumber);
//
//			writer.println("Player Full Name: " + playerStat.fullName);
//		}
//		
//		writer.flush();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
