package net.jroux.musicgen.lib;

import java.util.ArrayList;

public class Matrix {
	
	private ArrayList<ArrayList<Double>> matrix;
	
	private ArrayList<Double> maxCache;
	
	private ArrayList<Double> minCache;
	
	private int iSize;
	
	private int jSize;
	
	public Matrix(int iSize, int jSize) {
		matrix = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < iSize; i++)
			matrix.add(new ArrayList<Double>(jSize));
		this.iSize = iSize;
		this.jSize = jSize;
		initCache();
	}

	private void initCache() {
		maxCache = new ArrayList<Double>();
		minCache = new ArrayList<Double>();
		for(int i = 0; i < iSize; i++) {
			maxCache.add(-1.0);
			minCache.add(-1.0);
		}
	}
	
	public double get(int i, int j) {
		return matrix.get(i).get(j).doubleValue();
	}
	
	public void set(int i, int j, double value) {
		matrix.get(i).set(j, value);
	}
	
	public void normalize() {
		for(int i = 0; i < iSize; i++) {
			double sum = sum(i);
			if(sum != 0) {
				for (int j = 0; j < jSize; j++) {
					this.set(i, j, this.get(i, j) / sum);
				}
			}
		}
	}
	
	private double sum(int pos) {
		double sum = 0;
		for(double val : matrix.get(pos)) {
			sum += val;
		}
		return sum;
	}
	
	public double getMaxElement(int pos) {
		if (maxCache.get(pos) - 1.0 < 0.0001) {
			maxCache.set(pos, findMaxElement(pos));
		}
		return maxCache.get(pos);
	}

	public double getMinElement(int pos) {
		if (minCache.get(pos) - 1.0 < 0.0001) {
			minCache.set(pos, this.findMinPositiveElement(pos));
		}
		return minCache.get(pos);
	}
	
	private double findMaxElement(int pos) {
		double max = this.get(pos, 0);
		for(double val : matrix.get(pos)) {
			if(val > max) {
				max = val;
			}
		}
		return max;
	}
	
	private double findMinPositiveElement(int pos){
		double min = 1;
		for(double val : matrix.get(pos)) {
			if(val < min && val > 0) {
				min = val;
			}
		}
		return min;
	}
	
	public int getISize() {
		return iSize;
	}
	
	public int getJSize() {
		return jSize;
	}
	
}
