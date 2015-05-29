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
	
	public void updateWeights(Integer prev[]){
		int i = createIndex(Arrays.copyOfRange(prev, 0, prev.length - 1));
		int j = prev[prev.length - 1];
		if(matrix.get(i, j) > 0)
			matrix.set(i, j , matrix.get(i, j) + 1);
		else
			matrix.set(i, j, 1);
	}

	private int createIndex(Integer[] prev) {
		int temp = 0;
		for (int i = 0; i < prev.length - 1; i++) {
			temp += (N - 1) * prev[i];
		}
		temp += prev[prev.length - 1];
		return temp ;
	}

	public void normalizeMatrix() {
		matrix.normalize();
	}
	
	public int nextNote(Integer previous[]) {
		double rnd = Math.random();
    	double sum;
		int index = createIndex(previous);
		double max = matrix.getMaxElement(index);
		double min = matrix.getMinElement(index);
		if(max != 1) {
			sum = rnd * (max - min) + min;
			ArrayList<Double> temp = new ArrayList<>();
			for(int j = 0; j < matrix.getJSize(); j++) {
				if(matrix.get(index, j) > 0)
					temp.add(Math.pow((sum - matrix.get(index, j)), 2));
				else 
					temp.add(100.0);
			}
			int newIndex = findIndexMinValue(temp);
			System.out.println(newIndex);		
			return newIndex;
		} 
		for(int j = 0; j < matrix.getJSize(); j++) {
			if(matrix.get(index, j) == 1) {
				return j;
			}
		}
		return -3;
	}
	
	private int findIndexMinValue(ArrayList<Double> temp) {
		double currentValue = temp.get(0); 
		int smallestIndex = 0;
		for (int j = 1; j < temp.size(); j++) {
			if (temp.get(j) <= currentValue){ 
				currentValue = temp.get(j);
				smallestIndex = j;
			}
		}
		return smallestIndex;
	}
	 
}
