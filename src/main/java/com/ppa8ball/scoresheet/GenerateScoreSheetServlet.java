package com.ppa8ball.scoresheet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ppa8ball.scoresheet.service.ScoreSheetGenerator;
import com.ppa8ball.scoresheet.service.ScoreSheetGeneratorServiceImply;

/**
 * Servlet implementation class GenerateScoreSheet
 */
public class GenerateScoreSheetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateScoreSheetServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		final String homeString = req.getParameter("home");
		final String awayString = req.getParameter("away");
		final String isHomeString = req.getParameter("ishome");
		final String playersString = req.getParameter("players");
		
		
		final int home = Integer.parseInt(homeString);
		final int away = Integer.parseInt(awayString);
		final boolean isHome = Boolean.parseBoolean(isHomeString);
		
		
		ScoreSheetGenerator generator = new ScoreSheetGeneratorServiceImply();
		
		generator.GenerateScoreSheet(response, home, away, isHome);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
