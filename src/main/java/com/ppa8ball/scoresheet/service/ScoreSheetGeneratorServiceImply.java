package com.ppa8ball.scoresheet.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import com.ppa8ball.Scoresheet;
import com.ppa8ball.ScoresheetGenerator;
import com.ppa8ball.TeamRoster;
import com.ppa8ball.stats.PlayersStat;
import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.service.PlayerService;
import com.ppa8ball.stats.service.PlayerServiceImpl;
import com.ppa8ball.stats.service.TeamService;
import com.ppa8ball.stats.service.TeamsImpl;

public class ScoreSheetGeneratorServiceImply implements ScoreSheetGenerator
{
	
	public void GenerateScoreSheet(HttpServletResponse resp, Scoresheet scoresheet) throws IOException
	{
		try
		{

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			ScoresheetGenerator.generateScoreSheet(baos, scoresheet);

			resp.setContentType("application/pdf");

			resp.setHeader("Content-disposition", "inline; filename='javatpoint.pdf'");
			resp.setContentLength(baos.size());
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = resp.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (DocumentException e)
		{
			resp.setContentType("text/plain");
			resp.getWriter().println("Unable to generate spreadsheet.");
			return;
		}
	}

	
	public void GenerateScoreSheet(HttpServletResponse resp, int homeTeamNumber, int awayTeamNumber, boolean isHome) throws IOException
	{
		TeamService teamService =  new TeamsImpl();
		TeamStat homeTeamStat = teamService.Get(homeTeamNumber);
		TeamStat awayTeamStat = teamService.Get(awayTeamNumber);

		TeamRoster homeTeamRoster;
		TeamRoster awayTeamRoster;
		
		PlayerService playerService = new PlayerServiceImpl();

		if (isHome)
		{
			PlayersStat homePlayersStat = playerService.GetPlayerByTeam(homeTeamNumber);
			homeTeamRoster = new TeamRoster(homeTeamStat, homePlayersStat);
			awayTeamRoster = new TeamRoster(awayTeamStat);
		} else
		{
			PlayersStat awayPlayersStats = playerService.GetPlayerByTeam(awayTeamNumber);
			homeTeamRoster = new TeamRoster(homeTeamStat);
			awayTeamRoster = new TeamRoster(awayTeamStat, awayPlayersStats);
		}
		Scoresheet scoresheet = new Scoresheet(homeTeamRoster, awayTeamRoster);
		
		GenerateScoreSheet(resp, scoresheet);

	}
}
