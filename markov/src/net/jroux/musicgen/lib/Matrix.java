package net.jroux.musicgen.lib;

import java.util.ArrayList;
import java.util.Hashtable;

public class Matrix {
	
	private Hashtable<Integer, Double> matrix;
	
	private ArrayList<Double> maxCache;
	
	private ArrayList<Double> minCache;
	
	private int iSize;
	
	private int jSize;
	
	public Matrix(int iSize, int jSize) {
		matrix = new Hashtable<Integer, Double>();
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
	
	private int getKey(int i, int j) {
		return i * jSize + j; 
	}
	
	public double get(int i, int j) {
		int key = getKey(i, j);
		if(matrix.containsKey(key)) {
		   return matrix.get(key);
		}
		return -1;
	}
	
	public void set(int i, int j, double value) {
		int key = getKey(i, j);
		if(matrix.containsKey(key)) {
			matrix.replace(key, matrix.get(key) ,value);
		}
		matrix.put(key, value);
	}
	
	public void normalize() {
		for(int i = 0; i < iSize; i++) {
			double sum = sum(i);
			if(sum > 0) {
				for (int j = 0; j < jSize; j++) {
					if(this.get(i, j) > 0)
						this.set(i, j, this.get(i, j) / sum);
				}
			}
		}
	}
	
	private double sum(int pos) {
		double sum = 0;
		for(int i = 0; i < jSize; i++) {
			sum += this.get(pos, i);
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
		double max = 0 ;
		for(int i = 0; i < jSize; i++){
			if(this.get(pos, i) > 0) {
				max = this.get(pos, i);
			}
		}
		for(int i = 0; i < jSize; i++) {
			if(this.get(pos, i) > max) {
				max = this.get(pos, i);
			}
		}
		return max;
	}
	
	private double findMinPositiveElement(int pos){
		double min = 1;
		for(int i = 0; i < jSize; i++) {
			if(this.get(pos, i) < min && this.get(pos, i) > 0) {
				min = this.get(pos, i);
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
