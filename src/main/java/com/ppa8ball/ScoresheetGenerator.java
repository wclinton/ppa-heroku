package com.ppa8ball;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

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

	static public void generateScoreSheet(OutputStream writer, String date, int week, Scoresheet scoresheet) throws IOException,
			DocumentException
	{
		URL url = new URL(ScoreSheetUrl);

		PdfReader reader = new PdfReader(url);
		PdfStamper stamper;
		// preserve the reader enabling by creating a PDF in append mode (or
		// not)

	
		stamper = new PdfStamper(reader, writer);
		AcroFields form = stamper.getAcroFields();
		

		SetHomeFields(form, scoresheet.getHomeTeam());
		SetAwayFields(form, scoresheet.getAwayTeam());

		form.setField(WeekField, Integer.toString(week));
		form.setField(DateField, date);

		stamper.close();
		reader.close();
	}

	static private void SetHomeFields(AcroFields form, TeamRoster teamRoster) throws IOException, DocumentException
	{
		form.setField(HomeTeamNameField, teamRoster.name);
		form.setField(HomeTeamNumberField, Integer.toString(teamRoster.number));

		//form.setField(HomeTableField, "H Tbl");

		int i = 0;
		for (String s : HomeTeamAverageFields)
		{
			if (teamRoster.players[i] != null)
				form.setField(s, Double.toString(teamRoster.players[i++].average));
		}

		i = 0;
		for (String s : HomeTeamPlayerFields)
		{
			if (teamRoster.players[i] != null)
				form.setField(s, teamRoster.players[i++].name);
		}

	}

	static private void SetAwayFields(AcroFields form, TeamRoster teamRoster) throws IOException, DocumentException
	{
		form.setField(AwayTeamNameField, teamRoster.name);
		form.setField(AwayTeamNumberField, Integer.toString(teamRoster.number));
		// form.setField(HomeTableField, "H Tbl");

		int i = 0;
		for (String s : AwayTeamAverageFields)
		{
			if (teamRoster.players[i] != null)
				form.setField(s, Double.toString(teamRoster.players[i++].average));
		}

		i = 0;
		for (String s : AwayTeamPlayerFields)
		{
			if (teamRoster.players[i] != null)
				form.setField(s, teamRoster.players[i++].name);
		}
	}
}
