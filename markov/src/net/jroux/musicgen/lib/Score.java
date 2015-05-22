package net.jroux.musicgen.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
		return temp;
	}
	
	public void normalizeMatrix() {
		for (int i = 0; i < this.size; i++) {
			int sum = sumAll(i);
			if (sum !=0) {
				for (int j = 0; j < matrix[i].length; j++) {
					matrix[i][j] /= sum;
				}
			}
		}
	}

	private int sumAll(int pos) {
		int sum = 0;
		for(int i = 0; i < 128; sum += matrix[pos][i++]);
		return sum;
	}
	
	public int nextNote(Integer previos[]) {
		double rnd = Math.random();
		double sum = 0;
		int index = createIndex(previos);
		ArrayList<Double> temp = new ArrayList<Double>();
		for(int i = 0;  i < matrix[0].length; i++) {
			temp.add(matrix[index][i]);
		}
	    double max = Collections.max(temp);
	    sum = rnd * max;
		for(int j = 0; j < matrix[0].length; j++) {
			temp.set(j, Math.pow(sum - matrix[index][j], 2));
		}
		return temp.indexOf(Collections.min(temp));
	}	 
	 
}
