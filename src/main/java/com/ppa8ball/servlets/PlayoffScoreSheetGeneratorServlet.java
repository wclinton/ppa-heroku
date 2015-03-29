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
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.PlayerView;
import com.ppa8ball.viewmodel.RosterViewModel;

public class PlayoffScoreSheetGeneratorServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4741749441576480399L;

	protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException
	{
		final String homeTeamString = req.getParameter("homeTeamNumber");
		final String awayTeamString = req.getParameter("awayTeamNumber");

		final String homeRosterJson = req.getParameter("homeRoster");
		final String awayRosterJson = req.getParameter("awayRoster");

		final String dateString = req.getParameter("date");
		final String table1 = req.getParameter("table1");
		final String table2 = req.getParameter("table2");

		final int homeTeamNumber = Integer.parseInt(homeTeamString);
		final int awayTeamNumber = Integer.parseInt(awayTeamString);

		Gson gson = new Gson();

        RosterViewModel homeRoster = gson.fromJson(homeRosterJson, RosterViewModel.class);
        RosterViewModel awayRoster = gson.fromJson(awayRosterJson, RosterViewModel.class);

		Session session = HibernateUtil.getSessionFactory().openSession();

		Season currentSeason = new SeasonServiceImpl(session).GetCurrent();

		TeamService teamService = new TeamServiceImpl(session);

		Team homeTeam = teamService.GetByNumber(currentSeason, homeTeamNumber);

		Team awayTeam = teamService.GetByNumber(currentSeason, awayTeamNumber);

		Scoresheet scoresheet = new Scoresheet(homeTeam, awayTeam);

		scoresheet.setWeek(0);
		scoresheet.setDate(dateString);
		scoresheet.setTable1(table1);
		scoresheet.setTable2(table2);
		
		
		List<PlayerView> homePlayerList = Arrays.asList(homeRoster.players);
		List<PlayerView> awayPlayerList = Arrays.asList(awayRoster.players);

		scoresheet.setHomePlayers(homePlayerList);

		scoresheet.setAwayPlayers(awayPlayerList);

		ScoreSheetGenerator generator = new ScoreSheetGeneratorServiceImply();
		generator.GenerateScoreSheet(response, scoresheet);
		
		session.close();
	}
}