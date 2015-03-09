package com.ppa8ball.schedule.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.schedule.Match;

public class MatchServiceImpl implements MatchService
{
	Connection connection;
	public MatchServiceImpl(Connection connection)
	{
		this.connection = connection;
	}

	public void Save(Match match)
	{
		String stm = "INSERT INTO match(weekId, match, homeTeam, awayTeam, table1, table2) VALUES(?, ?,?,?,?,?)";
		PreparedStatement pst;
		try
		{
			
			pst = connection.prepareStatement(stm);

			int idx = 1;

			pst.setInt(idx++, match.weekId);
			pst.setInt(idx++, match.match);
			pst.setInt(idx++, match.homeTeam);
			pst.setInt(idx++, match.awayTeam);
			pst.setString(idx++, match.table1);
			pst.setString(idx++, match.table2);

			pst.executeUpdate();
			
			pst.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Save(Iterable<Match> matches)
	{
		for (Match match : matches)
		{
			Save(match);
		}
	}

	public Iterable<Match> GetByWeek(int week)
	{
		try
		{
			Statement stmt = connection.createStatement();
			List<Match> matches = new ArrayList<Match>();

			ResultSet rs = stmt.executeQuery("SELECT * FROM match");
			while (rs.next())
			{
				Match match = new Match(rs);
				matches.add(match);
			}
			rs.close();
			stmt.close();
			return matches;

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Match GetByTeam(int week, int teamNumber)
	{
		Iterable<Match> matches = GetByWeek(week);

		for (Match match : matches)
		{
			if (match.homeTeam == teamNumber || match.awayTeam == teamNumber)
				return match;
		}

		return null;
	}

	@Override
	public void DropTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.execute("DROP TABLE match");
			stmt.close();
		} catch (Exception e)
		{

		}
	}

	@Override
	public void CreateTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE match " + "(  id serial NOT NULL," + "weekId serial NOT NULL," + "homeTeam integer,"
					+ "awayTeam integer," + "table1 character varying," + "match integer," + "table2 character varying,"
					+ "CONSTRAINT match_pkey PRIMARY KEY (id)" + ")" + "WITH (  OIDS=FALSE);");
			stmt.close();
		} catch (Exception e)
		{
		}

	}
}
