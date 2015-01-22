package com.ppa8ball;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.ppa8ball.schedule.Match;
import com.ppa8ball.schedule.Week;
import com.ppa8ball.stats.PlayerStat;
import com.ppa8ball.stats.TeamStat;




public class OfyService {
	private static ObjectifyFactory factory;
    static {
    	
    	factory = new ObjectifyFactory();
    	
        factory.register(TeamStat.class);
        factory.register(PlayerStat.class);
        factory.register(Match.class);
        factory.register(Week.class);
    }

    public static Objectify myOfy() {
        return factory.begin();
    }

//    public static ObjectifyFactory factory() {
//        return ObjectifyService.factory();
//    }
}
