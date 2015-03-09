package com.ppa8ball.stats.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.db.DbDriver;
import com.ppa8ball.stats.PlayerStat;
import com.ppa8ball.stats.PlayersStat;

public class PlayerServiceImpl implements PlayerService
{
	Connection connection = DbDriver.getConnection();

	public PlayerServiceImpl()
	{
	}

	public PlayerStat GetPlayer(Long id)
	{
		// TODO - fill this in so that it returns a player by id.

		return null;
	}

	public PlayersStat GetPlayerByTeam(int teamNumber)
	{

		List<PlayerStat> playerStats = getPlayersFromDB(teamNumber);

//		if (playerStats.isEmpty())
//		{
//			loadDb();
//			playerStats = getPlayersFromDB(teamNumber);
//		}
		return new PlayersStat(playerStats);
	}

	private List<PlayerStat> getPlayersFromDB(int teamNumber)
	{
		try
		{
			Statement stmt = connection.createStatement();

			List<PlayerStat> players = new ArrayList<PlayerStat>();

			ResultSet rs = stmt.executeQuery("SELECT * FROM playerStat WHERE teamNumber = " + teamNumber);
			while (rs.next())
			{
				PlayerStat playerStat = new PlayerStat(rs);
				players.add(playerStat);
			}

			return players;

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// TODO - this method should be moved somewhere else
//	public static void loadDb()
//	{
//		Stats s = new Stats();
//
//		s.load();
//
//		List<PlayerStat> players = s.getPlayerStats();
//		Save(players);
//
//		List<TeamStat> teams = s.getTeams();
//		// OfyService.myOfy().save().entities(teams).now();
//	}

	public PlayersStat GetSparePlayers()
	{
		return GetPlayerByTeam(11);
	}

	public void Save(PlayerStat playerStat)
	{
		String stm = "INSERT INTO playerStat(teamNumber, firstName, lastName, fullName, gender, totalPoints, gamesPlayed, adjustedAverage, actualAverage, perfectNights) VALUES(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pst;
		try
		{
			pst = connection.prepareStatement(stm);

			int idx = 1;

			pst.setInt(idx++, playerStat.teamNumber);
			pst.setString(idx++, playerStat.firstName);
			pst.setString(idx++, playerStat.lastName);
			pst.setString(idx++, playerStat.fullName);
			pst.setInt(idx++, playerStat.gender.getValue());
			pst.setInt(idx++, playerStat.totalPoints);
			pst.setInt(idx++, playerStat.gamesPlayed);
			pst.setDouble(idx++, playerStat.adjustedAverage);
			pst.setDouble(idx++, playerStat.actualAverage);
			pst.setInt(idx++, playerStat.perfectNights);

			pst.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Save(PlayersStat playersStat)
	{
		Save(playersStat.players);
	}

	public void Save(Iterable<PlayerStat> playersStat)
	{
		for (PlayerStat playerStat : playersStat)
		{
			Save(playerStat);
		}
	}

	@Override
	public void DropTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.execute("DROP TABLE playerStat");
		} catch (Exception e)
		{
		}
	}

	@Override
	public void createTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.execute("CREATE TABLE playerStat (" + "id serial NOT NULL," + "teamNumber integer," + "firstName character varying," + "lastName character varying,"
					+ "fullName character varying," + "gender integer," + "totalPoints integer," + "gamesPlayed integer,"
					+ "adjustedAverage double precision," + "actualAverage double precision," + "perfectNights integer,"
					+ "CONSTRAINT playerStat_pkey PRIMARY KEY (id)) WITH ( OIDS=FALSE);");
		} catch (Exception e)
		{
			int i=0;
			i++;
		}
	}
}
