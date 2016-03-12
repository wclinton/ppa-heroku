package com.ppa8ball.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import com.ppa8ball.models.OrderedPlayer;
import com.ppa8ball.models.Player;
import com.ppa8ball.models.Roster;
import com.ppa8ball.models.Season;
import com.ppa8ball.models.Stat;
import com.ppa8ball.models.Team;
import com.ppa8ball.models.Week;
import com.ppa8ball.service.DataProcessService;
import com.ppa8ball.service.DataProcessServiceImpl;
import com.ppa8ball.service.MatchService;
import com.ppa8ball.service.MatchServiceImpl;
import com.ppa8ball.service.OrderedPlayerService;
import com.ppa8ball.service.OrderedPlayerServiceImpl;
import com.ppa8ball.service.PlayerService;
import com.ppa8ball.service.PlayerServiceImpl;
import com.ppa8ball.service.RosterService;
import com.ppa8ball.service.RosterServiceImpl;
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
import com.ppa8ball.viewmodel.RosterView;
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
      Session session = getSessionAndStartTransaction();

      SeasonView s = new SeasonView(getCurrentSeason(session));
      session.getTransaction().commit();

     // s.setCurrentStatsAvailable(true);

      return s;
   }

   private Season getCurrentSeason(Session session)
   {
      SeasonService seasonService = new SeasonServiceImpl(session);
      Season currentSeason = seasonService.GetCurrent();
      return currentSeason;
   }

   @GET
   @Path("/loadStats")
   public Response loadStats() throws ServletException, IOException
   {
      Session session = getSessionAndStartTransaction();
      Season season = new Season(seasons2014Year);
      DataProcessService service = new DataProcessServiceImpl(session);
      service.Clear();
      service.Process(season);
      Season season2015 = new Season(seasons2015Year);
      service.Process(season2015);
      String info = "done <br>";// + getDataInfo(season2015);
      session.getTransaction().commit();
      return Response.status(200).entity(info).build();
   }

   @GET
   @Path("/matches/{seasonId}/{weekNumber}/{teamNumber}")
   @Produces(MediaType.APPLICATION_JSON)
   public MatchView getMatch(@PathParam("seasonId") long seasonId, @PathParam("weekNumber") int week,
         @PathParam("teamNumber") int teamNumber)
   {
      Session session = getSessionAndStartTransaction();
      MatchService service = new MatchServiceImpl(session);
      Match match = service.getMatchByWeekTeam(seasonId, week, teamNumber);

      MatchView matchView = new MatchView(match);

      session.getTransaction().commit();

      return matchView;
   }

   @GET
   @Path("/teamPlayers/{seasonId}/{teamNumber}")
   @Produces(MediaType.APPLICATION_JSON)
   public PlayersView getTeamlayers(@PathParam("seasonId") long seasonId, @PathParam("teamNumber") int teamNumber)
   {
      Session session = getSessionAndStartTransaction();
      PlayersView pv = getPlayers(session, seasonId, teamNumber);
      session.getTransaction().commit();
      return pv;
   }

   @GET
   @Path("/sparePlayers/{seasonId}")
   @Produces(MediaType.APPLICATION_JSON)
   public PlayersView getSparePlayers(@PathParam("seasonId") long seasonId)
   {
      Session session = getSessionAndStartTransaction();
      PlayersView pv = getPlayers(session, seasonId, 11);
      PlayersView pv2 = getPlayers(session, seasonId, 12);

      pv.addPlayers(pv2);
      session.getTransaction().commit();
      return pv;
   }

   @GET
   @Path("generatePlayoffScoreSheet")
   @Produces("application/pdf")
   public Response generateScoreSheet(@QueryParam("homeTeam") final int homeTeamNumber,
         @QueryParam("awayTeam") final int awayTeamNumber, @QueryParam("homeRosterId") final long homeRosterId,
         @QueryParam("awayRosterId") final long awayRosterId, @QueryParam("date") final String date,
         @QueryParam("table1") final String table1, @QueryParam("table2") final String table2) throws Exception
   {
      StreamingOutput stream = new StreamingOutput()
      {
         @Override
         public void write(OutputStream output) throws IOException, WebApplicationException
         {
            try
               {

                  Session session = getSessionAndStartTransaction();

                  RosterService rosterService = new RosterServiceImpl(session);

                  Roster homeRoster = rosterService.Get(homeRosterId);
                  Roster awayRoster = rosterService.Get(awayRosterId);

                  RosterView homeRosterView = new RosterView(homeRoster);
                  RosterView awayRosterView = new RosterView(awayRoster);

                  List<PlayerView> homePlayers = Arrays.asList(homeRosterView.rosterPlayers);
                  List<PlayerView> awayPlayers = Arrays.asList(awayRosterView.rosterPlayers);

                  Scoresheet scoresheet = new Scoresheet(homeRoster.getTeam(), awayRoster.getTeam());
                  scoresheet.setDate(date);
                  scoresheet.setTable1(table1);
                  scoresheet.setTable2(table2);

                  scoresheet.setHomePlayers(homePlayers);

                  scoresheet.setAwayPlayers(awayPlayers);

                  ScoresheetGenerator.generateScoreSheet(output, scoresheet);

                  session.getTransaction().commit();

               } catch (Exception e)
               {
                  throw new WebApplicationException(e);
               }
         }
      };

      return Response.ok(stream).header("content-disposition", "inline; filename='javatpoint.pdf'").build();
   }

   @GET
   @Path("generateScoreSheet/{seasonId}")
   @Produces("application/pdf")
   public Response generateScoreSheet(@PathParam("seasonId") long seasonId,
         @QueryParam("myTeam") final int myTeamNumber, @QueryParam("opponentTeam") final int opponentTeamNumber,
         @QueryParam("ishome") final boolean isHome, @QueryParam("roster") final String roster,
         @QueryParam("week") final int week, @QueryParam("date") final String date,
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

                  final long sid;

                  if (isHome)
                     {
                        home = myTeamNumber;
                        away = opponentTeamNumber;
                     } else
                     {
                        home = opponentTeamNumber;
                        away = myTeamNumber;
                     }

                  Session session = getSessionAndStartTransaction();

                  if (seasonId == 0)
                     {
                        SeasonService seasonService = new SeasonServiceImpl(session);

                        Season season = seasonService.GetCurrent();

                        sid = season.getId();
                     } else
                     sid = seasonId;

                  TeamService teamService = new TeamServiceImpl(session);
                  Team homeTeam = teamService.GetByNumber(sid, home);
                  Team awayTeam = teamService.GetByNumber(sid, away);
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

                  session.getTransaction().commit();

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
      Session session = getSessionAndStartTransaction();
      SeasonService seasonService = new SeasonServiceImpl(session);

      Season season;

      if (seasonId == 0)
         season = seasonService.GetCurrent();
      else
         season = seasonService.Get(seasonId);
      TeamService teamService = new TeamServiceImpl(session);
      List<Team> teams = teamService.GetNormalBySeason(season);

      TeamsView teamView = new TeamsView(teams);

      session.getTransaction().commit();
      return teamView;
   }

   @GET
   @Path("/weeks/{seasonId}")
   @Produces(MediaType.APPLICATION_JSON)
   public WeeksView getWeeks(@PathParam("seasonId") long seasonId)
   {
      Session session = getSessionAndStartTransaction();

      SeasonService seasonService = new SeasonServiceImpl(session);
      Season season = seasonService.Get(seasonId);

      WeekService service = new WeekServiceImpl(session);
      List<Week> weeks = service.getAll(season);
      WeeksView weeksView = new WeeksView(weeks);

      session.getTransaction().commit();
      return weeksView;
   }
   
   @GET
   @Path("/currentPlayers")
   @Produces(MediaType.APPLICATION_JSON)
   public PlayersView getCurrentPlayers()
   {
      Session session = getSessionAndStartTransaction();
      Season season = getCurrentSeason(session);
      PlayersView players = new PlayersView();

      for (int i = 1; i <= 10; i++)
         {
            PlayersView playersTeam = getPlayers(session, season.getId(), i);
            players.addPlayers(playersTeam);
         }
      session.getTransaction().commit();

      return players;
   }

   @GET
   @Path("/players/{seasonId}")
   @Produces(MediaType.APPLICATION_JSON)
   public PlayersView getPlayers(@PathParam("seasonId") long seasonId)
   {
      Session session = getSessionAndStartTransaction();
      PlayersView players = new PlayersView();

      for (int i = 1; i <= 10; i++)
         {
            PlayersView playersTeam = getPlayers(session, seasonId, i);
            players.addPlayers(playersTeam);
         }
      session.getTransaction().commit();

      return players;
   }

   @POST
   @Path("/roster")
   @Produces(MediaType.APPLICATION_JSON)
   public Response consumeJSON(RosterView rosterView)
   {
      Session session = getSessionAndStartTransaction();

      SeasonService seasonService = new SeasonServiceImpl(session);
      Season season = seasonService.GetCurrent();

      Roster roster = new Roster();
      roster.setHome(rosterView.isHome);
      roster.setSeason(season);

      TeamService teamService = new TeamServiceImpl(session);
      Team team = teamService.GetByNumber(season.getId(), rosterView.teamId);

      roster.setTeam(team);

      List<OrderedPlayer> orderedPlayers = new ArrayList<OrderedPlayer>();

      int i = 0;

      PlayerService playerService = new PlayerServiceImpl(session);
      for (PlayerView playerView : rosterView.rosterPlayers)
         {
            Player p = playerService.Get(playerView.getId());

            OrderedPlayer orderPlayer = new OrderedPlayer();

            orderPlayer.setPlayer(p);
            orderPlayer.setOrder(i++);
            orderedPlayers.add(orderPlayer);
         }

      roster.setPlayers(orderedPlayers);

      RosterService rosterService = new RosterServiceImpl(session);

      Roster currentRoster = rosterService.getByTeam(season, team, rosterView.isHome);

      if (currentRoster != null)
         {
            RosterService service = new RosterServiceImpl(session);
            service.Delete(currentRoster.getId());
         }

      session.getTransaction().commit();

      Session session2 = getSessionAndStartTransaction();

      OrderedPlayerService orderedPlayerService = new OrderedPlayerServiceImpl(session2);

      for (OrderedPlayer orderedPlayer : orderedPlayers)
         {
            orderedPlayerService.Save(orderedPlayer);
         }

      RosterService rosterService2 = new RosterServiceImpl(session2);
      rosterService2.Save(roster);

      session2.getTransaction().commit();

      return Response.status(200).build();
   }

   @GET
   @Path("/roster/{teamId}/{isHome}")
   @Produces(MediaType.APPLICATION_JSON)
   public RosterView getRoster(@PathParam("teamId") int teamNumber, @PathParam("isHome") boolean isHome)
   {
      Session session = getSessionAndStartTransaction();

      SeasonService seasonService = new SeasonServiceImpl(session);
      Season season = seasonService.GetCurrent();

      TeamService teamService = new TeamServiceImpl(session);
      Team team = teamService.GetByNumber(season.getId(), teamNumber);

      RosterService rosterService = new RosterServiceImpl(session);

      Roster roster = rosterService.getByTeam(season, team, isHome);

      RosterView rosterView = new RosterView(roster);

      session.getTransaction().commit();

      return rosterView;
   }

   private PlayersView getPlayers(Session session, long seasonID, int teamNumber)
   {
      TeamService service = new TeamServiceImpl(session);
      Team team = service.GetByNumber(seasonID, teamNumber);

      Season season = new SeasonServiceImpl(session).Get(seasonID);

      PlayersView players = new PlayersView();

      for (Player player : team.getPlayers())
         {
            Stat stat = player.getStats(season);
            players.getPlayers().add(new PlayerView(player, stat));
         }

      return players;
   }

   // private String getDataInfo(Season season) throws IOException {
   // Session session = HibernateUtil.getSession();
   //
   // TeamService teamService = new TeamServiceImpl(session);
   // List<Team> savedTeams = teamService.GetBySeason(season);
   //
   // PlayerService playerService = new PlayerServiceImpl(session);
   //
   // List<Player> players = playerService.Get();
   //
   // String s = "Data uploaded for season:" + season.getDescription();
   // s += "\n";
   // s += "Teams:" + savedTeams.size();
   // s += "\n";
   // s += "Players:" + players.size();
   //
   // session.close();
   //
   // return s;
   // }

   private Session getSession()
   {
      return HibernateUtil.getSessionFactory().getCurrentSession();
   }

   private Session getSessionAndStartTransaction()
   {
      Session s = null;
      try
         {
            s = getSession();
            s.beginTransaction();
            return s;
         } finally
         {
            if (s != null)
               s.clear();
         }

   }

   // private Team getTeam(Session session, int teamId) {
   // TeamService service = new TeamServiceImpl(session);
   // return service.Get(teamId);
   // }

}