//package com.ppa8ball.schedule.service;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.ppa8ball.schedule.Match;
//import com.ppa8ball.schedule.Week;
//import com.ppa8ball.schedule.Weeks;
//
//public class WeekServiceImpl implements WeekService
//{
//	private Connection connection;
//
//	public WeekServiceImpl(Connection connection)
//	{
//		this.connection = connection;
//	}
//
//	public Weeks GetAll()
//	{
//		try
//		{
//
//			Statement stmt = connection.createStatement();
//			List<Week> weeks = new ArrayList<Week>();
//
//			ResultSet rs = stmt.executeQuery("SELECT * FROM week");
//			while (rs.next())
//			{
//				Week week = new Week(rs);
//				weeks.add(week);
//			}
//
//			rs.close();
//			stmt.close();
//
//			MatchService matchService = new MatchServiceImpl(connection);
//
//			for (Week week : weeks)
//			{
//				Iterable<Match> matches = matchService.GetByWeek(week.number);
//
//				week.setMatches((List<Match>) matches);
//			}
//
//			return new Weeks(weeks);
//
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	public void Save(Week week)
//	{
//		String stm = "INSERT INTO week (season, number, date) VALUES(?,?,?)";
//		PreparedStatement pst;
//
//		try
//		{
//			pst = connection.prepareStatement(stm, Statement.RETURN_GENERATED_KEYS);
//
//			int idx = 1;
//
//			pst.setString(idx++, week.season);
//			pst.setInt(idx++, week.number);
//			pst.setDate(idx++, week.date);
//
//			pst.executeUpdate();
//
//			ResultSet generatedKeys = pst.getGeneratedKeys();
//			if (generatedKeys.next())
//			{
//				week.id = generatedKeys.getInt("id");
//			}
//			pst.close();
//
//			if (week.getMatches() != null)
//			{
//				MatchService matchService = new MatchServiceImpl(connection);
//				for (Match match : week.getMatches())
//				{
//					match.weekId = week.id;
//					matchService.Save(match);
//				}
//			}
//
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void Save(Iterable<Week> weeks)
//	{
//		for (Week week : weeks)
//		{
//			Save(week);
//		}
//	}
//
//	@Override
//	public void CreateTable()
//	{
//		Statement stmt;
//		try
//		{
//			stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE TABLE week (" + " id serial NOT NULL," + "season character varying," + "number integer,"
//					+ "date date," + "CONSTRAINT week_pkey PRIMARY KEY (id)" + ") WITH (OIDS=FALSE); ");
//
//			stmt.close();
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void DropTable()
//	{
//		try
//		{
//			Statement stmt = connection.createStatement();
//			stmt.execute("DROP TABLE week");
//
//			stmt.close();
//		} catch (Exception e)
//		{
//
//		}
//	}
//
//	@Override
//	public Week Get(int weekNumber)
//	{
//		Statement stmt;
//		try
//		{
//			stmt = connection.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM week WHERE number = " + weekNumber);
//			rs.next();
//
//			Week week = new Week(rs);
//
//			rs.close();
//			stmt.close();
//
//			return week;
//		} catch (SQLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//}
