package com.ppa8ball.stats.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.stats.TeamStat;
import com.ppa8ball.stats.TeamsStat;

public class TeamsImpl implements TeamService
{
	private Connection connection;
	
	public TeamsImpl(Connection connection)
	{
		this.connection = connection;
	}
	
	public TeamsStat GetAll()
	{
		List<TeamStat> teams = getFromDB();

		// if (teams.isEmpty())
		// {
		// PlayerServiceImpl.loadDb();
		// teams = getFromDB();
		// }
		return new TeamsStat(teams);
	}

	public TeamStat Get(int teamNumber)
	{
		try
		{
			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM teamStat WHERE number =" + teamNumber);
			rs.next();

			TeamStat teamStat = new TeamStat(rs);
			
			
			rs.close();
			
			stmt.close();
			return teamStat;
			
			

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private List<TeamStat> getFromDB()
	{
		try
		{
			Statement stmt = connection.createStatement();

			List<TeamStat> teams = new ArrayList<TeamStat>();

			ResultSet rs = stmt.executeQuery("SELECT * FROM teamStat WHERE isNormal = true");
			while (rs.next())
			{
				TeamStat teamStat = new TeamStat(rs);
				teams.add(teamStat);
			}
			
			rs.close();
			stmt.close();

			return teams;

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void Save(TeamStat team)
	{
		String stm = "INSERT INTO teamStat(number, name, isSpare, isNoPlayer, isNormal) VALUES(?,?,?,?,?)";
		
		PreparedStatement pst;
		try
		{
			pst = connection.prepareStatement(stm);

			int idx = 1;

			pst.setInt(idx++, team.number);
			pst.setString(idx++, team.name);
			pst.setBoolean(idx++, team.isSpare);
			pst.setBoolean(idx++, team.isNoPlayer);
			pst.setBoolean(idx++, team.isNormal);
			
			
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void Save(Iterable<TeamStat> teams)
	{
		for (TeamStat teamStat : teams)
		{
			Save(teamStat);
		}
	}

	@Override
	public void DropTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.execute("DROP TABLE teamStat");
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
			stmt.execute("CREATE TABLE teamStat"
					+ "(id serial NOT NULL, number integer, name character varying, isSpare boolean, isNoPlayer boolean, isNormal boolean,"
					+ "CONSTRAINT teamStat_pkey PRIMARY KEY (id)" + ")WITH ( OIDS=FALSE);");
			stmt.close();
		} catch (Exception e)
		{
		}
	}
}