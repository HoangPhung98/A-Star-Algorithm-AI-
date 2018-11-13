package algorithm;

import java.util.Arrays;

import data.*;
import graphic.*;

public class Main {
//	public static int[][] matrix;
//	static int[] heuristic;
//	static final int n = 10;
	private static final int SOURCE = 0;
	private static final int DESTINATION = 9;
	private static int ROW = 10;
	private static int COLLUM = 10;
	static MainWindow mainWindow;
	public static void main(String[] args) {
		init();	
//		Algorithm algorithm = new Algorithm(matrix, heuristic, n, SOURCE, DESTINATION);
//		algorithm.FindPath();
//		algorithm.PrintPath();
	}
	private static void init() {
//		Data dataObj = new Data(); --remove
//		matrix = dataObj.getMatrix(); --remove
//		heuristic = dataObj.getHeuristic(); --remove
//		n = dataObj.getN();  --remove
//		matrix = new int[ROW][COLLUM];
//		for(int i=0; i<ROW; i++) Arrays.fill(matrix[i], 0);
		mainWindow = new MainWindow(ROW, COLLUM);
		
	}
}
