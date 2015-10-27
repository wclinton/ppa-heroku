package com.ppa8ball.util;

import java.net.URI;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	
	public static Session getSession()
	{
		return getSessionFactory().openSession();
	}

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

			// only add the ssl if we are running in production.
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
			
			

			cfg.setProperty("hibernate.c3p0.max_size","19");
			cfg.setProperty("hibernate.c3p0.min_size","1");
			cfg.setProperty("hibernate.c3p0.timeout","1800");
			//cfg.setProperty("hibernate.c3p0.max_statements","100");
			//cfg.setProperty("hibernate.c3p0.idle_test_period","300");
			//cfg.setProperty("hibernate.c3p0.acquire_increment","2");
			
			cfg.setProperty("hibernate.format_sql", "true");
			cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

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
