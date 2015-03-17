package com.ppa8ball.util;

import java.net.URI;
import java.sql.DriverManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory()
	{
		try
		{
			URI dbUri = new URI(System.getenv("DATABASE_URL"));
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
			dbUrl += "?user=" + username;
			dbUrl += "&password=" + password;

			//only add the ssl if we are running in production.
			if (!dbUri.getHost().contains("localhost"))
			{
				dbUrl += "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
			}
			/** Load the hibernate.cfg.xml from the classpath **/
			Configuration cfg = new Configuration();

			cfg.configure();
			cfg.setProperty("hibernate.connection.url", dbUrl);
			cfg.setProperty("connection.username", username);
			cfg.setProperty("connection.password", password);

			cfg.setProperty("hibernate.connection.requireSSL", "true");

			SessionFactory sessionFactory = cfg.buildSessionFactory();

			return sessionFactory;

		} catch (Throwable ex)
		{
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public static void shutdown()
	{
		// Close caches and connection pools
		getSessionFactory().close();
	}
}
