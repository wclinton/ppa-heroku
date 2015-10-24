package com.ppa8ball.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.ppa8ball.Scoresheet;
import com.ppa8ball.ScoresheetGenerator;
import com.ppa8ball.models.Match;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Stat;
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

@Path("")
public class RestService
{
	private static final int seasons2014Year = 2014;
	private static final int seasons2015Year = 2015;

	@GET
	@Path("/currentSeason")
	@Produces(MediaType.APPLICATION_JSON)
	public SeasonView getCurrentSeason()
	{
		Session session = getSession();

		SeasonService seasonService = new SeasonServiceImpl(session);
		Season currentSeason = seasonService.GetCurrent();

		return new SeasonView(currentSeason);
	}

	@GET
	@Path("/loadStats")
	public Response loadStats() throws ServletException, IOException
	{
		Season season = new Season(seasons2014Year);
		DataProcessService service = new DataProcessServiceImpl();

		// clear all the data from the database.
		service.Clear();

		service.Process(season);

		String info = getDataInfo(season);

		Season season2015 = new Season(seasons2015Year);
		service.Process(season2015);

		info = "<br>" + getDataInfo(season2015);
		return Response.status(200).entity(info).build();
	}

	@GET
	@Path("/matches/{seasonId}/{weekNumber}/{teamNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public MatchView getMatch(@PathParam("seasonId") long seasonId, @PathParam("weekNumber") int week,
			@PathParam("teamNumber") int teamNumber)
	{
		Session session = HibernateUtil.getSession();
		MatchService service = new MatchServiceImpl(session);
		Match match = service.getMatchByWeekTeam(seasonId, week, teamNumber);

		return new MatchView(match);
	}

	@GET
	@Path("/teamPlayers/{seasonId}/{teamNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayersView getTeamlayers(@PathParam("seasonId") long seasonId, @PathParam("teamNumber") int teamNumber)
	{
		return getPlayers(seasonId, teamNumber);
	}

	@GET
	@Path("/sparePlayers/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlayersView getSparePlayers(@PathParam("seasonId") long seasonId)
	{
		return getPlayers(seasonId, 11);
	}

	@GET
	@Path("generateScoreSheet/{seasonId}")
	@Produces("application/pdf")
	public Response generateScoreSheet(@PathParam("seasonId") final long seasonId, @QueryParam("myTeam") final int myTeamNumber,
			@QueryParam("opponentTeam") final int opponentTeamNumber, @QueryParam("ishome") final boolean isHome,
			@QueryParam("roster") final String roster, @QueryParam("week") final int week, @QueryParam("date") final String date,
			@QueryParam("table1") final String table1, @QueryParam("table2") final String table2) throws Exception
	{
		StreamingOutput stream = new StreamingOutput()
		{
			@Override
			public void write(OutputStream output) throws IOException, WebApplicationException
			{
				try
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
					TeamService teamService = new TeamServiceImpl(session);
					Team homeTeam = teamService.GetByNumber(seasonId, home);
					Team awayTeam = teamService.GetByNumber(seasonId, away);
					Scoresheet scoresheet = new Scoresheet(homeTeam, awayTeam);

					scoresheet.setWeek(week);
					scoresheet.setDate(date);
					scoresheet.setTable1(table1);
					scoresheet.setTable2(table2);

					if (isHome)
						scoresheet.setHomePlayers(players);
					else
						scoresheet.setAwayPlayers(players);

					ScoresheetGenerator.generateScoreSheet(output, scoresheet);

				} catch (Exception e)
				{
					throw new WebApplicationException(e);
				}
			}

		};

		return Response.ok(stream).header("content-disposition", "inline; filename='javatpoint.pdf'").build();
	}

	@GET
	@Path("/teams/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TeamsView getTeams(@PathParam("seasonId") long seasonId)
	{
		Session session = getSession();
		SeasonService seasonService = new SeasonServiceImpl(session);
		Season season = seasonService.Get(seasonId);
		TeamService teamService = new TeamServiceImpl(session);
		List<Team> teams = teamService.GetNormalBySeason(season);
		TeamsView teamView = new TeamsView(teams);
		return teamView;
	}

	@GET
	@Path("/weeks/{seasonId}")
	@Produces(MediaType.APPLICATION_JSON)
	public WeeksView getWeeks(@PathParam("seasonId") long seasonId)
	{
		Session session = getSession();
		
		SeasonService seasonService = new SeasonServiceImpl(session);
		Season season = seasonService.Get(seasonId);
		
		WeekService service = new WeekServiceImpl(session);
		List<Week> weeks = service.getAll(season);
		WeeksView weeksView = new WeeksView(weeks);
		return weeksView;
	}

	private PlayersView getPlayers(long seasonID, int teamNumber)
	{
		
		TeamService service = new TeamServiceImpl(getSession());
		Team team = service.GetByNumber(seasonID, teamNumber);
		
		Season season = new SeasonServiceImpl(getSession()).Get(seasonID);

		PlayersView players = new PlayersView();

		for (Player player : team.getPlayers())
		{
			Stat stat = player.getStats(season);
			players.getPlayers().add(new PlayerView(player,stat));
		}

		return players;
	}

	private String getDataInfo(Season season) throws IOException
	{
		Session session = HibernateUtil.getSession();

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
		return HibernateUtil.getSession();
	}
}