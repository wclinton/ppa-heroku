package com.ppa8ball.stats;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStat
{
	public int id;
	public int teamNumber;
	public String firstName;
	public String lastName;
	public String fullName;

	public Gender gender;

	public int totalPoints;
	public int gamesPlayed;
	public double adjustedAverage;
	public double actualAverage;
	public int perfectNights;

	public double displayAdjustedAverage;
	public double displayActualAverage;

	public PlayerStat()
	{
	}

	public PlayerStat(ResultSet rs)
	{
		try
		{
			id = rs.getInt("id");
			teamNumber = rs.getInt("teamNumber");
			firstName = rs.getString("firstName");
			lastName = rs.getString("lastName");
			fullName = rs.getString("fullName");
			gender = Gender.fromInteger(rs.getInt("gender"));

			totalPoints = rs.getInt("totalPoints");

			gamesPlayed = rs.getInt("gamesPlayed");

			adjustedAverage = rs.getDouble("adjustedAverage");
			actualAverage = rs.getDouble("actualAverage");

			perfectNights = rs.getInt("perfectNights");

			displayActualAverage = RoundTo1Decimals(actualAverage);
			displayAdjustedAverage = RoundTo1Decimals(adjustedAverage);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString()
	{
		return "Player:" + fullName + " Team:" + teamNumber;
	}

	private static double RoundTo1Decimals(double d)
	{
		double rounded = Math.round(10 * d);
		return rounded / 10;
	}
}
