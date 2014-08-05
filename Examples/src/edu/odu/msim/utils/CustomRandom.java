package edu.odu.msim.utils;

import java.text.DecimalFormat;
import java.util.Random;

public class CustomRandom extends Random
{
	private static final long serialVersionUID = 1L;

	public static final int MIN = 1;
	public static final int MAX = 100;
	
	public CustomRandom()
	{
		super();
	}
	
	public CustomRandom(long seed)
	{
		super(seed);
	}
	
	@Override
	public double nextDouble()
	{
		// made our custom random, since SimpleMatrix doesn't handle decimal places.
		int randomNumber = Math.abs(super.nextInt(MAX - MIN) + MIN);
		
		double newValue = roundTo2Decimals(randomNumber);
		
		return newValue;
	}
	
	public double roundTo2Decimals(double val) 
	{
        DecimalFormat df2 = new DecimalFormat("####.00");
        return Double.valueOf(df2.format(val));
	}
	
}
