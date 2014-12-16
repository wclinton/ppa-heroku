package com.ppa8ball;
import java.text.DecimalFormat;

public class Player
{
	public String name;
	public double average;

	public Player(String name, double average)
	{
		this.name = name;
		this.average = average;
	}

	public Player(PlayerStat playerStat)
	{
		this.name = playerStat.fullName;
		this.average = RoundTo2Decimals(playerStat.adjustedAverage);
	}

	double RoundTo2Decimals(double val)
	{
		DecimalFormat df2 = new DecimalFormat("###.#");
		return Double.valueOf(df2.format(val));
	}
}
