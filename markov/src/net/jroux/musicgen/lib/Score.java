package net.jroux.musicgen.lib;

import java.util.ArrayList;
import java.util.Arrays;


public class Score {
	
	public int range;
	
	private int size;
	
	private final int N = 128;
	
	public double matrix[][];
	
	public Score(int range){
		this.range = range;
		this.size = (int)Math.pow(N, this.range); 
		this.matrix = new double[size][N];
	}
	
	public void updateWeights(Integer previos[]){
		matrix[createIndex(Arrays.copyOfRange(previos, 0, previos.length - 1))][previos[previos.length - 1]]++;
	}

	private int createIndex(Integer[] previos) {
		int temp = 0;
		for (int i = 0; i < previos.length - 1; i++) {
			temp += (N - 1) * previos[i];
		}
		temp += previos[previos.length - 1];
		return temp ;
	}
	
	public void normalizeMatrix() {
		for (int i = 0; i < this.size; i++) {
			double sum = sumAll(i);
			if (sum !=0) {	
				for (int j = 0; j < matrix[i].length; j++) {
					matrix[i][j] /= sum;
				}
			}
		}
	}

	private double sumAll(int pos) {
		double sum = 0;
		for(int i = 0; i < 128; sum += matrix[pos][i++]);
		return sum;
	}
	
	public int nextNote(Integer previos[]) {
		double rnd = Math.random();
    	double sum = 0;
		int index = createIndex(previos);
		double max = findMaxElement(index);
		double min = findMinPositiveElement(index); 
	    sum = rnd * (max - min) + min;
	    ArrayList<Double> temp = new ArrayList<Double>();
		for(int j = 0; j < matrix[0].length; j++) {
			temp.add( Math.pow(matrix[index][j] - sum, 2));
		}
		int newIndex = findIndexMinValue(temp);
		return newIndex;
	}	 
	
	private double findMinPositiveElement(int index) {
		double min = 1;
		for ( int i = 1; i < matrix[index].length; i++) {
		    if ( matrix[index][i] < min && matrix[index][i] > 0) {
		      min = matrix[index][i];
		    }
		}
		return min;
	}

	private int findIndexMinValue(ArrayList<Double> temp) {
		double currentValue = temp.get(0); 
		int smallestIndex = 0;
		for (int j=1; j < temp.size(); j++) {
			if (temp.get(j) <= currentValue){ 
				currentValue = temp.get(j);
				smallestIndex = j;
			}
		}
		return smallestIndex;
	}

	private double findMaxElement(int index) {
		double max = matrix[index][0];
		for ( int i = 1; i < matrix[index].length; i++) {
		    if ( matrix[index][i] > max) {
		      max = matrix[index][i];
		    }
		}
		return max;
	}

	
	 
}
