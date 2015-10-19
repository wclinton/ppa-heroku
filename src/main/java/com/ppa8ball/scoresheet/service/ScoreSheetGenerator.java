package com.ppa8ball.scoresheet.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.ppa8ball.Scoresheet;

public interface ScoreSheetGenerator
{
	public void GenerateScoreSheet(HttpServletResponse resp, Scoresheet scoresheet) throws IOException;
	//public void GenerateScoreSheet(HttpServletResponse resp, int homeTeamNumber, int awayTeamNumber, boolean isHome) throws IOException;
}
