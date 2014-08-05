package edu.odu.msim.utils;

import java.util.Random;

import org.ejml.ops.RandomMatrices;
import org.ejml.simple.SimpleMatrix;

public class MatrixUtils 
{	
	public static SimpleMatrix makeRandomMatrix(int rows, int columns, 
		double min, double max)
	{
		// create a matrix whose elements are drawn from uniform distributions
		Random rand = new CustomRandom(System.currentTimeMillis());
		SimpleMatrix matrix = SimpleMatrix.wrap(RandomMatrices.createRandom(
			rows, columns, min, max, rand));

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < columns; j++)
			{
				sb.append(matrix.get(i, j));
				sb.append(',');
			}
		}

		return matrix;
	}
}
