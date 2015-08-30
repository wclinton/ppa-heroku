package com.ppa8ball.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.ppa8ball.dataloader.Stats;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
import com.ppa8ball.schedule.Load;
import com.ppa8ball.service.DataProcessService;
import com.ppa8ball.service.DataProcessServiceImpl;
import com.ppa8ball.service.MatchService;
import com.ppa8ball.service.MatchServiceImpl;
import com.ppa8ball.service.PlayerService;
import com.ppa8ball.service.PlayerServiceImpl;
import com.ppa8ball.service.SeasonService;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.service.WeekService;
import com.ppa8ball.service.WeekServiceImpl;
import com.ppa8ball.util.HibernateUtil;

/**
 * Servlet implementation class LoadStats
 */
public class LoadStatsServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String seasonYear = "2014-2015";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Season season = new Season(seasonYear);
		DataProcessService service = new DataProcessServiceImpl();
		
		service.Process(season);
		
		String info = getDataInfo(season);
		response.setContentType("text/plain");
		PrintWriter writer = response.getWriter();
		writer.print(info);
		writer.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
	}
	
	private String getDataInfo(Season season) throws IOException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		TeamService teamService = new TeamServiceImpl(session);
		List<Team> savedTeams = teamService.GetBySeason(season);
		
		PlayerService playerService = new PlayerServiceImpl(session);
		
		List<Player> players = playerService.Get();
		
		String s= "Data uploaded for season:"+season.getDescription();
		
		s+="Teams:"+savedTeams.size();
		s+="Players:"+ players.size();
		
		session.close();
		
		return s;
	}
}