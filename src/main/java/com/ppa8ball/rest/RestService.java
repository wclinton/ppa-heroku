package com.ppa8ball.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.ppa8ball.models.Match;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.service.DataProcessService;
import com.ppa8ball.service.DataProcessServiceImpl;
import com.ppa8ball.service.MatchService;
import com.ppa8ball.service.MatchServiceImpl;
import com.ppa8ball.service.PlayerService;
import com.ppa8ball.service.PlayerServiceImpl;
import com.ppa8ball.service.TeamService;
import com.ppa8ball.service.TeamServiceImpl;
import com.ppa8ball.servlets.JsonHelper;
import com.ppa8ball.util.HibernateUtil;
import com.ppa8ball.viewmodel.MatchView;

@Path("rest")
public class RestService
{
	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
 
		String output = "Jersey say : " + msg;
 
		return Response.status(200).entity(output).build();
 
	}
	private static final int seasonStartYear = 2014;
	
	@GET
	@Path("/LoadStats")
	public Response loadStats() throws ServletException, IOException
	{
		Season season = new Season(seasonStartYear);
		DataProcessService service = new DataProcessServiceImpl();
		
		service.Process(season);
		
		String info = getDataInfo(season);
		return Response.status(200).entity(info).build();
	}
	
	
	
	@GET
	@Path("Matches/{week}/{teamNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public MatchView getMatch(@PathParam("week") String weekNumberString, @PathParam("teamNumber") String teamNumberString)
	{
	 final int week = Integer.parseInt(weekNumberString);
	 final int teamNumber = Integer.parseInt(teamNumberString);
	 
	 Session session = HibernateUtil.getSessionFactory().openSession();
	 
	 MatchService service  = new MatchServiceImpl(session);
	 
	 Match match = service.getMatchByWeekTeam(week, teamNumber);
	 
	 
	return new MatchView(match);
	 
	}
	
	private String getDataInfo(Season season) throws IOException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		TeamService teamService = new TeamServiceImpl(session);
		List<Team> savedTeams = teamService.GetBySeason(season);
		
		PlayerService playerService = new PlayerServiceImpl(session);
		
		List<Player> players = playerService.Get();
		
		String s= "Data uploaded for season:"+season.getDescription();
		s+="\n";
		s+="Teams:"+savedTeams.size();
		s+="\n";
		s+="Players:"+ players.size();
		
		session.close();
		
		return s;
	}

}
