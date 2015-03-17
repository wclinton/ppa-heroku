package com.ppa8ball;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.ppa8ball.models.Team;
import com.ppa8ball.viewmodel.PlayerView;

public class ScoresheetGenerator
{

	private static final String ScoreSheetUrl = "http://www.ppa8ball.com/Scoresheet.pdf";

	private static final String WeekField = "Week";
	private static final String DateField = "Date";

	// Home Team fields
	private static final String HomeTeamNameField = "Home_Team_Name";
	private static final String HomeTeamNumberField = "Home_Team_Number";
	private static final String HomeTableField = "Home_Table";

	private static final String[] HomeTeamPlayerFields =
	{ "Home_Player1_Name", "Home_Player2_Name", "Home_Player3_Name", "Home_Player4_Name", "Home_Player5_Name" };
	private static final String[] HomeTeamAverageFields =
	{ "Home_Player1_Average", "Home_Player2_Average", "Home_Player3_Average", "Home_Player4_Average", "Home_Player5_Average", };

	private static final String HomeAverageTotal = "Home_Average_Total";

	// Away Team fields
	private static final String AwayTeamNameField = "Away_Team_Name";
	private static final String AwayTableField = "Away_Table";

	private static final String AwayTeamNumberField = "Away_Team_Number";

	private static final String[] AwayTeamAverageFields =
	{ "Away_Player1_Average", "Away_Player2_Average", "Away_Player3_Average", "Away_Player4_Average", "Away_Player5_Average" };

	private static final String[] AwayTeamPlayerFields =
	{ "Away_Player1_Name", "Away_Player2_Name", "Away_Player3_Name", "Away_Player4_Name", "Away_Player5_Name" };

	private static final String AwayAverageTotal = "Away_Average_Total";

	static public void generateScoreSheet(OutputStream writer, Scoresheet scoresheet) throws IOException, DocumentException
	{
		URL url = new URL(ScoreSheetUrl);

		PdfReader reader = new PdfReader(url);
		PdfStamper stamper;
		// preserve the reader enabling by creating a PDF in append mode (or
		// not)

		stamper = new PdfStamper(reader, writer);
		AcroFields form = stamper.getAcroFields();

		SetHomeFields(form, scoresheet.getHome(), scoresheet.getHomePlayers());
		SetAwayFields(form, scoresheet.getAway(), scoresheet.getAwayPlayers());

		form.setField(WeekField, Integer.toString(scoresheet.getWeek()));
		form.setField(DateField, scoresheet.getDate());
		form.setField(HomeTableField, scoresheet.getTable1());
		form.setField(AwayTableField, scoresheet.getTable2());

		stamper.close();
		reader.close();
	}

	static private void SetHomeFields(AcroFields form, Team team, List<PlayerView> players) throws IOException, DocumentException
	{
		form.setField(HomeTeamNameField, team.getName());
		form.setField(HomeTeamNumberField, Integer.toString(team.getNumber()));
		int i = 0;
		double totalAverage = 0;

		for (PlayerView playerView : players)
		{
			double average = playerView.getDisplayAdjustedAverage();
			totalAverage += average;

			String name = playerView.getFullName();
			if (!name.isEmpty())
			{
				form.setField(HomeTeamAverageFields[i], Double.toString(average));
				form.setField(HomeTeamPlayerFields[i], playerView.getFullName());
			}
			i++;
		}

		if (has5Players(players))
			form.setField(HomeAverageTotal, Double.toString(totalAverage));

	}

	static private void SetAwayFields(AcroFields form, Team team, List<PlayerView> players) throws IOException, DocumentException
	{
		form.setField(AwayTeamNameField, team.getName());
		form.setField(AwayTeamNumberField, Integer.toString(team.getNumber()));

		int i = 0;
		double totalAverage = 0;

		for (PlayerView playerView : players)
		{
			double average = playerView.getDisplayAdjustedAverage();
			totalAverage += average;

			String name = playerView.getFullName();
			if (!name.isEmpty())
			{
				form.setField(AwayTeamAverageFields[i], Double.toString(average));
				form.setField(AwayTeamPlayerFields[i], playerView.getFullName());
			}
			i++;
		}

		if (has5Players(players))
			form.setField(AwayAverageTotal, Double.toString(totalAverage));
	}

	private static boolean has5Players(List<PlayerView> players)
	{
		if (players.size() < 5)
			return false;

		for (PlayerView playerView : players)
		{
			if (playerView.getFullName().isEmpty())
				return false;
		}
		return true;
	}
}
