package com.ppa8ball.util;

import java.net.URI;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory = buildSessionFactory();

//	public static Session getSession()
//	{
//		Session session = getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		
//		return session;
//	}
	
	

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
			Configuration config = new Configuration();

			config.configure();
			config.setProperty("hibernate.connection.url", dbUrl);
			config.setProperty("connection.username", username);
			config.setProperty("connection.password", password);

			// cfg.setProperty("hibernate.connection.provider_class",
			// "org.hibernate.connection.C3P0ConnectionProvider");
			//
			// cfg.setProperty("hibernate.c3p0.acquire_increment", "1");
			// cfg.setProperty("hibernate.c3p0.idle_test_period", "0");
			// cfg.setProperty("hibernate.c3p0.min_size", "1");
			// cfg.setProperty("hibernate.c3p0.max_size", "5");
			// cfg.setProperty("hibernate.c3p0.timeout", "0");
			// cfg.setProperty("javax.persistence.validation.mode", "none");
			//
			// cfg.setProperty("hibernate.format_sql", "true");
			// cfg.setProperty("hibernate.dialect",
			// "org.hibernate.dialect.PostgreSQLDialect");
			//
			config.setProperty("hibernate.connection.requireSSL", "true");
			config.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
			
			config.setProperty("hibernate.jdbc.use_get_generated_keys",
			 "false");
			
			
			
			
			
			 config.setProperty("hibernate.hbm2ddl.auto", "update");
			    //config.setProperty("hibernate.cache.use_query_cache", "true");
			   // config.setProperty("hibernate.cache.use_second_level_cache", "true");
			    //config.setProperty("hibernate.cache.region.factory_class", "net.sf.ehcache.hibernate.EhCacheRegionFactory");
			 //   config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.EhCacheProvider");
			    //config.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
//			    config.setProperty("hibernate.jdbc.fetch_size", "100");
//			    config.setProperty("hibernate.jdbc.batch_size", "30");
			    config.setProperty("hibernate.jdbc.use_scrollable_resultset", "true");
			    config.setProperty("hibernate.connection.provider_class", "org.hibernate.connection.C3P0ConnectionProvider");

			    config.setProperty("hibernate.c3p0.acquire_increment", "1");
			    config.setProperty("hibernate.c3p0.idle_test_period", "200");
			    config.setProperty("hibernate.c3p0.min_size", "2");
			    config.setProperty("hibernate.c3p0.max_size", "15");
			    config.setProperty("hibernate.c3p0.timeout", "1000");
			    config.setProperty("javax.persistence.validation.mode", "none");
			
			SessionFactory sessionFactory = config.buildSessionFactory();

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
