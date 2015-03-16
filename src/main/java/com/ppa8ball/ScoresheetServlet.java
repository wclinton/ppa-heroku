package com.ppa8ball;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

@SuppressWarnings("serial")
public class ScoresheetServlet extends HttpServlet
{
	private static int[] playerOrder;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{

//		final String homeString = req.getParameter("home");
//		final String awayString = req.getParameter("away");
//		final String isHomeString = req.getParameter("ishome");
//		final String playersString = req.getParameter("players");
//		final String JsonString = req.getParameter("toJson");
//		
//		final int home;
//		final int away;
//		final boolean isHome;
//		final boolean toJson;
//		String[] players;
//
//		try
//		{
//			home = Integer.parseInt(homeString);
//			away = Integer.parseInt(awayString);
//			isHome = Boolean.parseBoolean(isHomeString);
//			
//			toJson = Boolean.parseBoolean(JsonString);
//
//			if (playersString != null)
//			{
//
//				playerOrder = new int[5];
//				players = playersString.split(",");
//				int i = 0;
//				for (String player : players)
//				{
//					playerOrder[i++] = Integer.parseInt(player);
//					if (i >= 5)
//						break;
//				}
//			}
//		}
//
//		catch (NumberFormatException e)
//		{
//			resp.setContentType("text/plain");
//			resp.getWriter().println("Unable to parse parameters.");
//			resp.getWriter().println("The correct params are:.");
//			return;
//		}
//
//		Stats s = new Stats();
//
//		s.load();
//
//		TeamStat homeTeamStat = s.getTeam(home);
//		TeamStat awayTeamStat = s.getTeam(away);
//
//		TeamRoster homeTeamRoster;
//		TeamRoster awayTeamRoster;
//
//		if (isHome)
//		{
//			List<PlayerStat> homePlayersStat = s.getPlayerStat(home, playerOrder);
//			homeTeamRoster = new TeamRoster(homeTeamStat, homePlayersStat);
//			awayTeamRoster = new TeamRoster(awayTeamStat);
//		} else
//		{
//			List<PlayerStat> awayPlayersStats = s.getPlayerStat(away, playerOrder);
//			homeTeamRoster = new TeamRoster(homeTeamStat);
//			awayTeamRoster = new TeamRoster(awayTeamStat, awayPlayersStats);
//		}
//		Scoresheet scoresheet = new Scoresheet(homeTeamRoster, awayTeamRoster);
//
//		try
//		{
//
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//			ScoresheetGenerator.generateScoreSheet(baos, scoresheet);
//
//			resp.setContentType("application/pdf");
//
//			resp.setHeader("Content-disposition", "inline; filename='javatpoint.pdf'");
//			resp.setContentLength(baos.size());
//			// write ByteArrayOutputStream to the ServletOutputStream
//			OutputStream os = resp.getOutputStream();
//			baos.writeTo(os);
//			os.flush();
//			os.close();
//
//		} catch (DocumentException e)
//		{
//			resp.setContentType("text/plain");
//			resp.getWriter().println("Unable to generate spreadsheet.");
//			return;
//		}
	}
	
	public static void main(String[] args) throws Exception{
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
       // context.addServlet(new ServletHolder(new Main()),"/*");
        server.start();
        server.join();
    }
}
