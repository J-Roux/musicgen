package net.jroux.musicgen.lib;

import java.util.ArrayList;
import java.util.Arrays;


public class Score {
	
	public int range;
	
	private int size;
	
	private final int N = 128;
	
	private Matrix matrix;
	
	public Score(int range){
		this.range = range;
		this.size = (int)Math.pow(N, this.range); 
		this.matrix = new Matrix(size, N);
	}
	
	public void updateWeights(Integer previos[]){
		int i = createIndex(Arrays.copyOfRange(previos, 0, previos.length - 1));
		int j = previos[previos.length - 1];
		matrix.set(i, j , matrix.get(i, j) + 1);
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
		matrix.normalize();
	}
	
	public int nextNote(Integer previos[]) {
		double rnd = Math.random();
    	double sum = 0;
		int index = createIndex(previos);
		double max = matrix.getMaxElement(index);
		double min = matrix.getMinElement(index); 
	    sum = rnd * (max - min) + min;
	    ArrayList<Double> temp = new ArrayList<Double>();
		for(int j = 0; j < matrix.getJSize(); j++) {
			temp.add( Math.pow((sum - matrix.get(index, j)), 2));
		}
		int newIndex = findIndexMinValue(temp);
		return newIndex;
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
	 
}
