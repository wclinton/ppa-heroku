package com.ppa8ball.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.ppa8ball.Scoresheet;
import com.ppa8ball.ScoresheetGenerator;
import com.ppa8ball.models.Match;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
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
import com.ppa8ball.viewmodel.MatchView;
import com.ppa8ball.viewmodel.PlayerView;
import com.ppa8ball.viewmodel.PlayersView;
import com.ppa8ball.viewmodel.SeasonView;
import com.ppa8ball.viewmodel.TeamsView;
import com.ppa8ball.viewmodel.WeeksView;

@Path("rest")
public class RestService
{
	// @GET
	// @Path("/{param}")
	// public Response getMsg(@PathParam("param") String msg)
	// {
	//
	// String output = "Jersey say : " + msg;
	//
	// return Response.status(200).entity(output).build();
	//
	// }

	private static final int seasonStartYear = 2014;

	@GET
	@Path("/CurrentSeason")
	@Produces(MediaType.APPLICATION_JSON)
	public SeasonView getCurrentSeason()
	{
		Session session = getSession();

		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();

		return new SeasonView(currentSeason);
	}

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
	@Path("/Matches/{seasonId}/{weekNumber}/{teamNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public MatchView getMatch(@PathParam("seasonId") long seasonId, @PathParam("weekNumber") int week,
			@PathParam("teamNumber") int teamNumber)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		MatchService service = new MatchServiceImpl(session);
		Match match = service.getMatchByWeekTeam(seasonId, week, teamNumber);

		return new MatchView(match);
	}

	@GET
	@Path("TeamPlayers/{seasonId}/{teamNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayersView getTeamlayers(@PathParam("seasonId") long seasonId, @PathParam("teamNumber") int teamNumber)
	{
		return getPlayers(seasonId, teamNumber);
	}

	@GET
	@Path("SparePlayers/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayersView getSparePlayers(@PathParam("seasonId") long seasonId)
	{
		return getPlayers(seasonId, 12);
	}

	@GET
	@Path("GenerateScoreSheet")
	@Produces("application/pdf")
	public Response generateScoreSheet(@QueryParam("myTeam") int myTeamNumber, @QueryParam("opponentTeam") int opponentTeamNumber,
			@QueryParam("ishome") boolean isHome, @QueryParam("roster") String roster, @QueryParam("week") int week,
			@QueryParam("date") String date, @QueryParam("table1") String table1, @QueryParam("table1") String table2) throws Exception
	{

		Gson gson = new Gson();
		List<PlayerView> players = Arrays.asList(gson.fromJson(roster, PlayerView[].class));

		final int home, away;

		if (isHome)
		{
			home = myTeamNumber;
			away = opponentTeamNumber;
		} else
		{
			home = opponentTeamNumber;
			away = myTeamNumber;
		}

		Session session = getSession();
		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();

		TeamService teamService = new TeamServiceImpl(session);

		Team homeTeam = teamService.GetByNumber(currentSeason.getId(), home);

		Team awayTeam = teamService.GetByNumber(currentSeason.getId(), away);

		Scoresheet scoresheet = new Scoresheet(homeTeam, awayTeam);

		scoresheet.setWeek(week);
		scoresheet.setDate(date);
		scoresheet.setTable1(table1);
		scoresheet.setTable2(table2);

		if (isHome)
			scoresheet.setHomePlayers(players);
		else
			scoresheet.setAwayPlayers(players);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ScoresheetGenerator.generateScoreSheet(baos, scoresheet);

		return Response.ok(baos).header("content-disposition", "inline; filename='javatpoint.pdf'").build();
	}

	// @GET
	// @Path("Teams/{seasonId}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public TeamsView getTeams(@PathParam("seasonId") long seasonId)
	@GET
	@Path("Teams")
	@Produces(MediaType.APPLICATION_JSON)
	public TeamsView getTeams()
	{
		Session session = getSession();

		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();

		TeamService teamService = new TeamServiceImpl(session);
		List<Team> teams = teamService.GetNormalBySeason(currentSeason);

		TeamsView teamView = new TeamsView(teams);

		return teamView;
	}

	// @GET
	// @Path("Weeks/{seasonId}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public WeeksView getWeeks(@PathParam("seasonId") long seasonId)

	@GET
	@Path("Weeks")
	@Produces(MediaType.APPLICATION_JSON)
	public WeeksView getWeeks()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		WeekService service = new WeekServiceImpl(session);

		List<Week> weeks = service.getAll();

		WeeksView weeksView = new WeeksView(weeks);

		return weeksView;
	}

	private PlayersView getPlayers(long seasonID, int teamNumber)
	{
		TeamService service = new TeamServiceImpl(getSession());
		Team team = service.GetByNumber(seasonID, teamNumber);

		PlayersView players = new PlayersView();

		for (Player player : team.getPlayers())
		{
			players.getPlayers().add(new PlayerView(player));
		}

		return players;
	}

	private String getDataInfo(Season season) throws IOException
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		TeamService teamService = new TeamServiceImpl(session);
		List<Team> savedTeams = teamService.GetBySeason(season);

		PlayerService playerService = new PlayerServiceImpl(session);

		List<Player> players = playerService.Get();

		String s = "Data uploaded for season:" + season.getDescription();
		s += "\n";
		s += "Teams:" + savedTeams.size();
		s += "\n";
		s += "Players:" + players.size();

		session.close();

		return s;
	}

	private static Session getSession()
	{
		return HibernateUtil.getSessionFactory().openSession();
	}
}
