package com.ppa8ball.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.TeamRoster;
import com.ppa8ball.service.PlayerService;
import com.ppa8ball.service.PlayerServiceImpl;
import com.ppa8ball.service.SeasonService;
import com.ppa8ball.service.SeasonServiceImpl;
import com.ppa8ball.service.TeamRosterService;
import com.ppa8ball.service.TeamRosterServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.PlayerView;
import com.ppa8ball.viewmodel.RosterViewModel;

public class RosterServlet extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RosterServlet()
	{
		super();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		// Get received JSON data from request
		BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
		String json = "";
		if (br != null)
		{
			json = br.readLine();
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
		// Gson gson = new Gson();
		// String json = gson.toJson(obj);

		RosterViewModel r = gson.fromJson(json, RosterViewModel.class);

		Session session = HibernateUtil.getSessionFactory().openSession();
		
		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();
		
		TeamService teamService = new TeamServiceImpl(session);
		Team team = teamService.GetByNumber(currentSeason, r.teamNumber);
		
		PlayerService playerService = new PlayerServiceImpl(session);
		
		List<Player> players = new ArrayList<Player>();
		
		for (PlayerView player : r.players)
		{
			Player p = playerService.Get(player.getId());
			players.add(p);
		}

		TeamRosterService service = new TeamRosterServiceImpl(session);
		
		TeamRoster teamRoster = service.GetRosterByTeam(team, r.isHome);
		
		if ( teamRoster == null)
		{
			teamRoster = new TeamRoster();
		}
		teamRoster.setIsHome(r.isHome);
		teamRoster.setTeam(team);
		teamRoster.setPlayers(players);

		service.Save(teamRoster);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		final String teamNumberString = req.getParameter("teamNumber");
		final int teamNumber = Integer.parseInt(teamNumberString);
		
		final boolean isHome = Boolean.parseBoolean(req.getParameter("isHome"));
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Season currentSeason = new SeasonServiceImpl(session).GetCurrent();
		
		TeamRosterService service = new  TeamRosterServiceImpl(session);
		
		Team team = new TeamServiceImpl(session).GetByNumber(currentSeason,teamNumber);
		
		TeamRoster teamRoster = service.GetRosterByTeam(team, isHome);
		
		RosterViewModel roster = convert(teamRoster);
		
		JsonHelper.ReturnJson(resp, (Object) roster);
	}
	
	private RosterViewModel convert(TeamRoster teamRoster)
	{
		RosterViewModel rsm = new RosterViewModel();
		
		rsm.isHome = teamRoster.getIsHome();
		rsm.teamNumber = teamRoster.getTeam().getNumber();
		
		Player[] players = {teamRoster.getPlayer1(), teamRoster.getPlayer2(),teamRoster.getPlayer3(),teamRoster.getPlayer4(),teamRoster.getPlayer5()};
		
		List<PlayerView> playerViews = new ArrayList<PlayerView>();
		
		for (Player player : players)
		{
			playerViews.add(new PlayerView(player));
		}
		
		rsm.players = playerViews.toArray(new PlayerView[0]);
		
		return rsm;
	}
	
	
}
