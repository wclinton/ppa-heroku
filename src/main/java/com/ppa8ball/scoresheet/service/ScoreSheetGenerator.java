package com.ppa8ball.scoresheet.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface ScoreSheetGenerator
{
	public void GenerateScoreSheet(HttpServletResponse resp, int homeTeamNumber, int awayTeamNumber, boolean isHome) throws IOException;
}
