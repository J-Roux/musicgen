package net.jroux.musicgen.lib;

public class Score {
	
	public int range;
	
	private int size;
	
	private final int N = 128;
	
	public float matrix[][];
	
	public Score(int range){
		this.range = range;
		this.size = (int)Math.pow(N, this.range); 
		this.matrix = new float[size][N];
	}
	
	public void updateWeights(Integer previos[]){
		matrix[createIndex(previos, previos.length - 1)][previos[previos.length - 1]]++;
	}

	private int createIndex(Integer[] previos,int length) {
		int temp = 0;
		for (int i = 0; i < length - 1; i++) {
			temp += (N - 1) * previos[i];
		}
		temp += previos[length - 1];
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
		float sum = 0;
		for (int i = 0; i < matrix[0].length; i++) {
			sum += matrix[createIndex(previos, previos.length - 1)][i];
			if (sum >= rnd) {
				return i;
			}
		}
		double temp =  rnd*127;
		return (int)temp;
	}
	 
	 
}
