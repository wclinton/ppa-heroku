package com.ppa8ball.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.ppa8ball.Scoresheet;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.scoresheet.service.ScoreSheetGenerator;
import com.ppa8ball.scoresheet.service.ScoreSheetGeneratorServiceImply;
import com.ppa8ball.service.SeasonService;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.PlayerView;

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
		final String weekString  = req.getParameter("week");
		final String dateString = req.getParameter("date");
		final String table1 = req.getParameter("table1");
		final String table2 = req.getParameter("table2");
		
		
		final int myTeam = Integer.parseInt(myTeamString);
		final int opponentTeam = Integer.parseInt(opponentTeamString);
		final boolean isHome = Boolean.parseBoolean(isHomeString);
		final int week = Integer.parseInt(weekString);
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		
		Gson gson = new Gson();
		
		
		
		List<PlayerView> players = Arrays.asList(gson.fromJson(roster, PlayerView[].class));
		
		
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
		
		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();
		
		TeamService teamService = new TeamServiceImpl(session);
		
		
		Team homeTeam = teamService.GetByNumber(currentSeason, home);
		
		Team awayTeam = teamService.GetByNumber(currentSeason, away);
		
//		TeamRoster homeTeamRoster=null;
//		TeamRoster awayTeamRoster=null;
		
//		if (isHome)
//		{
//		
//		 homeTeamRoster = new TeamRoster(homeTeamStat,players);
//		 awayTeamRoster = new TeamRoster(awayTeamStat);
//		}
//		else
//		{
//			 homeTeamRoster = new TeamRoster(homeTeamStat);
//			 awayTeamRoster = new TeamRoster(awayTeamStat,players);
//		}
		Scoresheet scoresheet = new Scoresheet(homeTeam, awayTeam);
		
		scoresheet.setWeek(week);
		scoresheet.setDate(dateString);
		scoresheet.setTable1(table1);
		scoresheet.setTable2(table2);
		
		if (isHome)
			scoresheet.setHomePlayers(players);
		else
			scoresheet.setAwayPlayers(players);
		
		ScoreSheetGenerator generator = new ScoreSheetGeneratorServiceImply();
		generator.GenerateScoreSheet(response, scoresheet);
		
		session.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
