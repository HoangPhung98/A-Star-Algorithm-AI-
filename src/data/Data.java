package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Data {

	private int n;
	private int[][] matrix;
	private int[] heuristic;
	private int[] name;
	
	private final String FILE_PATH="E:\\SourceCode\\Java\\AI_A_Star_Algorithm\\data\\data.txt";
	public Data() {
		readFile();
	}

	private void readFile() {
		try {
			File file = new File(FILE_PATH);
			BufferedReader br = new BufferedReader(new FileReader(file));
			n = Integer.parseInt(br.readLine());
			//read matrix
			this.matrix = new int[n][n];
			for(int i=0; i<n; i++) {
				String temp_arr_str = br.readLine();
				String temp_arr_str_splitted[] = temp_arr_str.split(" ");
				for(int j=0; j<n; j++) matrix[i][j] = Integer.parseInt(temp_arr_str_splitted[j]);
			}
			
			
			//read heuristic array
			heuristic = new int[n];
			for(int i=0; i<n; i++) {
				String temp_arr_str = br.readLine();
				String temp_arr_str_splitted[] = temp_arr_str.split(" ");
				heuristic[i]=Integer.parseInt(temp_arr_str_splitted[1]);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
	}
	public void printData() {

		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) System.out.print(matrix[i][j]+" ");
			System.out.println();
		}
		for(int i=0; i<n; i++) System.out.print(heuristic[i]+" ");
	}
	
	
	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int[] getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int[] heuristic) {
		this.heuristic = heuristic;
	}
}
