package com.ppa8ball.util;

import org.hibernate.Session;

public interface SessionFactory
{
	Session getSession();
}
