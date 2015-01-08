package com.ppa8ball.scoresheet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ppa8ball.Player;
import com.ppa8ball.Scoresheet;
import com.ppa8ball.TeamRoster;
import com.ppa8ball.scoresheet.service.ScoreSheetGenerator;
import com.ppa8ball.scoresheet.service.ScoreSheetGeneratorServiceImply;
import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.service.TeamService;
import com.ppa8ball.stats.service.TeamsImpl;

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
		final String myTeamString = req.getParameter("myTeam");
		final String opponentTeamString = req.getParameter("opponentTeam");
		final String isHomeString = req.getParameter("ishome");
		final String roster = req.getParameter("roster");
		
		final int myTeam = Integer.parseInt(myTeamString);
		final int opponentTeam = Integer.parseInt(opponentTeamString);
		final boolean isHome = Boolean.parseBoolean(isHomeString);
		
		Gson gson = new Gson();
		
		Player[] players = gson.fromJson(roster, Player[].class);
		
		
		final int home,away;
		
		
		if (isHome)
		{
			home = myTeam;
			away = opponentTeam;
		}
		else
		{
			home=opponentTeam;
			away = myTeam;
		}
		
		TeamService teamService = new TeamsImpl();
		
		
		TeamStat homeTeamStat = teamService.Get(home);
		
		TeamStat awayTeamStat = teamService.Get(away);
		
		TeamRoster homeTeamRoster;
		TeamRoster awayTeamRoster;
		
		if (isHome)
		{
		
		 homeTeamRoster = new TeamRoster(homeTeamStat,players);
		 awayTeamRoster = new TeamRoster(awayTeamStat);
		}
		else
		{
			 homeTeamRoster = new TeamRoster(homeTeamStat);
			 awayTeamRoster = new TeamRoster(awayTeamStat,players);
		}
		Scoresheet scoresheet = new Scoresheet(homeTeamRoster, awayTeamRoster);
		
		ScoreSheetGenerator generator = new ScoreSheetGeneratorServiceImply();
		generator.GenerateScoreSheet(response, scoresheet);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
