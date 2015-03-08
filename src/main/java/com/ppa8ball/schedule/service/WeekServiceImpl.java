package com.ppa8ball.schedule.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ppa8ball.db.DbDriver;
import com.ppa8ball.schedule.Week;
import com.ppa8ball.schedule.Weeks;

public class WeekServiceImpl implements WeekService
{

	Connection connection = DbDriver.getConnection();

	public WeekServiceImpl()
	{
	}

	public Weeks GetAll()
	{
		Statement stmt = getStatement();

		try
		{
			List<Week> weeks = new ArrayList<Week>();

			ResultSet rs = stmt.executeQuery("SELECT * FROM week");
			while (rs.next())
			{
				Week week = new Week(rs);
				weeks.add(week);
			}
			
			return new Weeks(weeks);

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void Save(Week week)
	{
		String stm = "INSERT INTO week (season, number, date) VALUES(?,?,?)";
		PreparedStatement pst;
		
		try
		{
			pst = connection.prepareStatement(stm);

			int idx = 1;

			pst.setString(idx++, week.season);
			pst.setInt(idx++, week.number);
			pst.setDate(idx++, week.date);
		
			pst.executeUpdate();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Save(Iterable<Week> weeks)
	{
		for (Week week : weeks)
		{
			Save(week);
		}
	}

	@Override
	public void CreateTable()
	{
		Statement stmt;
		try
		{
			stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE week (" + " id serial NOT NULL," + "season character varying," + "number integer,"
					+ "date date," + "CONSTRAINT week_pkey PRIMARY KEY (id)" + ") WITH (OIDS=FALSE);");
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void DropTable()
	{
		try
		{
			Statement stmt = connection.createStatement();
			stmt.execute("DROP TABLE week");
		} catch (Exception e)
		{

		}

	}

	private Statement getStatement()
	{
		try
		{
			return connection.createStatement();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
