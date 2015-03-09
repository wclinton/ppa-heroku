package com.ppa8ball.stats;

public enum Gender
{
	Male(0), Female(1), Unknown(2);

	private final int value;

	Gender(int value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}
	
	public static Gender fromInteger(int x) 
	{
		switch (x)
		{
		case 0:
			return Gender.Male;
		case 1: 
			return Gender.Female;
		case 2:
			return Gender.Unknown;
		}
		
		return null;
	}    
    
}
