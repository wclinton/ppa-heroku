package com.ppa8ball;
import java.text.DecimalFormat;

import com.ppa8ball.stats.PlayerStat;

public class Player
{
	public String name;
	public double average;
	
	public Player()
	{
		
	}

	public Player(String name, double average)
	{
		this.name = name;
		this.average = average;
	}

	public Player(PlayerStat playerStat)
	{
		this.name = playerStat.fullName;
		this.average = RoundTo1Decimals(playerStat.adjustedAverage);
	}

	private double RoundTo1Decimals(double val)
	{
		DecimalFormat df2 = new DecimalFormat("###.#");
		return Double.valueOf(df2.format(val));
	}
}
