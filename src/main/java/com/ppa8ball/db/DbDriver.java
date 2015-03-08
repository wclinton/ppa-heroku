package com.ppa8ball.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbDriver
{

	public static Connection getConnection()
	{
		try
		{
			URI dbUri = new URI(System.getenv("DATABASE_URL"));
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() +dbUri.getPath();
			return DriverManager.getConnection(dbUrl, username, password);
		} catch (URISyntaxException e)
		{
int i=0;
i++;
		} catch (SQLException e)
		{

			int i=0;
			
			i++;
		}

		return null;
	}
}
