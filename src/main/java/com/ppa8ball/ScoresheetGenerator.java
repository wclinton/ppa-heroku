package com.ppa8ball;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
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

   private static final String[] HomeTeamPlayerFields = { "Home_Player1_Name", "Home_Player2_Name", "Home_Player3_Name",
         "Home_Player4_Name", "Home_Player5_Name" };
   private static final String[] HomeTeamAverageFields = { "Home_Player1_Average", "Home_Player2_Average",
         "Home_Player3_Average", "Home_Player4_Average", "Home_Player5_Average", };

   private static final String HomeAverageTotal = "Home_Average_Total";

   private static final String[] HomeLegHandicaps = { "Home_Leg1_Handicap", "Home_Leg2_Handicap", "Home_Leg3_Handicap",
         "Home_Leg4_Handicap", "Home_Leg5_Handicap" };

   private static final String HomeTotalHandicap = "Home_Total_Handicap";

   // Away Team fields
   private static final String AwayTeamNameField = "Away_Team_Name";
   private static final String AwayTableField = "Away_Table";

   private static final String AwayTeamNumberField = "Away_Team_Number";

   private static final String[] AwayTeamAverageFields = { "Away_Player1_Average", "Away_Player2_Average",
         "Away_Player3_Average", "Away_Player4_Average", "Away_Player5_Average" };

   private static final String[] AwayTeamPlayerFields = { "Away_Player1_Name", "Away_Player2_Name", "Away_Player3_Name",
         "Away_Player4_Name", "Away_Player5_Name" };

   private static final String AwayAverageTotal = "Away_Average_Total";

   private static final String[] AwayLegHandicaps = { "Away_Leg1_Handicap", "Away_Leg2_Handicap", "Away_Leg3_Handicap",
         "Away_Leg4_Handicap", "Away_Leg5_Handicap" };
   private static final String AwayTotalHandicap = "Away_Total_Handicap";

   static public void generateScoreSheet(OutputStream writer, Scoresheet scoresheet)
         throws IOException, DocumentException
   {
      URL url = new URL(ScoreSheetUrl);

      PdfReader reader = new PdfReader(url);
      PdfStamper stamper;
      // preserve the reader enabling by creating a PDF in append mode (or
      // not)

      stamper = new PdfStamper(reader, writer);
      AcroFields form = stamper.getAcroFields();

      boolean homeHasNoPLayer = hasNoPlayer(scoresheet.getHomePlayers());
      boolean awayHasNoPlayer = hasNoPlayer(scoresheet.getAwayPlayers());

      SetHomeFields(form, scoresheet.getHome(), scoresheet.getHomePlayers(), awayHasNoPlayer);
      SetAwayFields(form, scoresheet.getAway(), scoresheet.getAwayPlayers(), homeHasNoPLayer);

      // if home team has averages && away team has averages ...

      int week = scoresheet.getWeek();

      if (week > 0)
         form.setField(WeekField, Integer.toString(week));
      form.setField(DateField, scoresheet.getDate());

      String table1 = scoresheet.getTable1();
      String table2 = scoresheet.getTable2();

      if (table1 != null && table1.compareToIgnoreCase("undefined") != 0)
         form.setField(HomeTableField, table1);
      else
         form.setField(HomeTableField, "");

      if (table2 != null && table2.compareToIgnoreCase("undefined") != 0)
         form.setField(AwayTableField, table2);
      else
         form.setField(AwayTableField, "");

      String awayAverageTotalString = form.getField(AwayAverageTotal).trim();
      String homeAverageTotalString = form.getField(HomeAverageTotal).trim();

      if (!awayAverageTotalString.isEmpty() && !homeAverageTotalString.isEmpty())
         {
            Double awayAverageTotal = Double.parseDouble(awayAverageTotalString);
            Double homeAverageTotal = Double.parseDouble(homeAverageTotalString);

            calcualteHandicap(form, homeAverageTotal, awayAverageTotal);
         }
      stamper.close();
      reader.close();
   }

   static private void SetHomeFields(AcroFields form, Team team, List<PlayerView> players, boolean onlyFourPlayers)
         throws IOException, DocumentException
   {
      form.setField(HomeTeamNameField, team.getName());
      form.setField(HomeTeamNumberField, Integer.toString(team.getNumber()));
      int i = 0;
      double totalAverage = 0;

      PlayerView maxPLayer = getMaxHandicapPlayer(players);

      for (PlayerView playerView : players)
         {
            if (i >= 5)
               break;
            double average = playerView.getDisplayAdjustedAverage();
            totalAverage += average;

            if (!playerView.getFullName().isEmpty())
               {
                  form.setField(HomeTeamAverageFields[i], Double.toString(average));

                  if (onlyFourPlayers && maxPLayer.getId() == playerView.getId())
                     {

                     } else
                     form.setField(HomeTeamPlayerFields[i], playerView.getFullName());
               }
            i++;
         }

      if (has5Players(players))
         form.setField(HomeAverageTotal, Double.toString(RoundTo1Decimals(totalAverage)));
   }

   static private void SetAwayFields(AcroFields form, Team team, List<PlayerView> players, boolean onlyFourPlayers)
         throws IOException, DocumentException
   {
      form.setField(AwayTeamNameField, team.getName());
      form.setField(AwayTeamNumberField, Integer.toString(team.getNumber()));

      int i = 0;
      double totalAverage = 0;

      PlayerView maxPLayer = getMaxHandicapPlayer(players);

      for (PlayerView playerView : players)
         {
            if (i >= 5)
               break;

            double average = playerView.getDisplayAdjustedAverage();
            totalAverage += average;

            String name = playerView.getFullName();
            if (!name.isEmpty())
               {
                  if (onlyFourPlayers && maxPLayer.getId() == playerView.getId())
                     {
                     } else
                     {
                        form.setField(AwayTeamAverageFields[i], Double.toString(average));
                     }
                  form.setField(AwayTeamPlayerFields[i], playerView.getFullName());
               }
            i++;
         }

      if (has5Players(players))
         form.setField(AwayAverageTotal, Double.toString(RoundTo1Decimals(totalAverage)));
   }

   private static void calcualteHandicap(AcroFields form, double homeAverage, double awayAverage)
         throws IOException, DocumentException
   {
      // Mathematical operations on floating points is always an issue.
      // specifically rounding issues can occur. Using BigDecimal to fix these
      // issues.

      final BigDecimal difference = BigDecimal.valueOf(homeAverage).subtract(BigDecimal.valueOf(awayAverage));

      BigDecimal rounded = difference.setScale(0, RoundingMode.HALF_UP);

      double handicap = rounded.doubleValue();

      if (handicap < 0.0)
         setHomeHandicap(form, -handicap);
      else if (handicap > 0.0)
         setAwayHandicap(form, handicap);
   }

   private static void setHomeHandicap(AcroFields form, double handicap) throws IOException, DocumentException
   {
      for (String leg : HomeLegHandicaps)
         {
            form.setField(leg, roundTo0Decimals(handicap));
         }
      form.setField(HomeTotalHandicap, roundTo0Decimals(handicap * 5));
   }

   private static void setAwayHandicap(AcroFields form, double handicap) throws IOException, DocumentException
   {
      for (String leg : AwayLegHandicaps)
         {
            form.setField(leg, roundTo0Decimals(handicap));
         }
      form.setField(AwayTotalHandicap, roundTo0Decimals(handicap * 5));
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

   private static double RoundTo1Decimals(double d)
   {
      double rounded = Math.round(10 * d);
      return rounded / 10;
   }

   private static String roundTo0Decimals(double d)
   {
      DecimalFormat df = new DecimalFormat("#");
      return df.format(Math.round(d));
   }

   private static boolean hasNoPlayer(List<PlayerView> players)
   {
      for (PlayerView playerView : players)
         {
            if (playerView.getActualAverage() == 0)
               return true;
         }

      return false;
   }

   private static PlayerView getMaxHandicapPlayer(List<PlayerView> players)
   {
      if (players.size() == 0)
         return null;
      PlayerView max = players.stream().max((p1, p2) -> Double.compare(p1.getActualAverage(), p2.getActualAverage()))
            .get();

      return max;
   }
}