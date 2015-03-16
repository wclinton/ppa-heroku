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

	private static String ScoreSheetUrl = "http://www.ppa8ball.com/Scoresheet.pdf";

	static final String WeekField = "Week";
	static final String DateField = "Date";

	// Home Team fields
	static final String HomeTeamNameField = "Home team name";
	static final String HomeTableField = "Table";
	static final String HomeTeamNumberField = "HOME TEAM";
	static final String[] HomeTeamPlayerFields =
	{ "HT Player1", "HT Player2", "HT Player3", "HT Player4", "HT Player5" };
	static final String[] HomeTeamAverageFields =
	{ "HT 1", "HT 2", "HT 3", "HT 4", "HT 5" };

	// Away Team fields
	static final String AwayTeamNameField = "Away team name";
	static final String AwayTableField = "Table_2";

	static final String AwayTeamNumberField = "AWAY TEAM";

	static final String[] AwayTeamAverageFields =
	{ "1", "2", "3", "4", "5" };

	static final String[] AwayTeamPlayerFields =
	{ "Player", "Player_2", "Player_3", "Player_4", "Player_5" };

	static final String PlayerField = "Player";

	static public void generateScoreSheet(OutputStream writer, Scoresheet scoresheet) throws IOException,
			DocumentException
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

	static private void SetHomeFields(AcroFields form, Team team, List<PlayerView> players ) throws IOException, DocumentException
	{
		form.setField(HomeTeamNameField, team.getName());
		form.setField(HomeTeamNumberField, Integer.toString(team.getNumber()));
		int i = 0;
		for (PlayerView playerView : players)
		{
			form.setField(HomeTeamAverageFields[i], Double.toString(playerView.getDisplayActualAverage()));
			form.setField(HomeTeamPlayerFields[i], playerView.getFullName());
			i++;
		}
	}

	static private void SetAwayFields(AcroFields form, Team team, List<PlayerView> players) throws IOException, DocumentException
	{
		form.setField(AwayTeamNameField, team.getName());
		form.setField(AwayTeamNumberField, Integer.toString(team.getNumber()));

		int i = 0;
		for (PlayerView playerView : players)
		{
			form.setField(AwayTeamAverageFields[i], Double.toString(playerView.getDisplayActualAverage()));
			form.setField(AwayTeamPlayerFields[i], playerView.getFullName());
			i++;
		}
	}
}
