package algorithm;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {
	private int n = 0;
	private final int SOURCE;
	private final int DESTINATION;
	private int[][] matrix;
	private int[] heuristic;
	private ArrayList<Integer> openingSet;
	private ArrayList<Integer> closeingSet;
	private int[] f;
	private int[] g;
	private int[] prev;

	public Algorithm(int[][] matrix, int[] heuristic, int n, int SOURCE, int DESTINATION) {
		this.matrix = matrix;
		this.heuristic = heuristic;
		this.n = n;
		this.SOURCE = SOURCE;
		this.DESTINATION = DESTINATION;
		
		openingSet = new ArrayList<>();
		openingSet.add(SOURCE) ;
		closeingSet = new ArrayList<>();
		f = Arrays.copyOf(heuristic,heuristic.length); //f = g + h, because at first g=0 => f = h;
		g = new int[n]; Arrays.fill(g,10000); //at first init g=infinity;
		g[0]=0;
		prev = new int[n];
		Arrays.fill(prev, -1);
		
	}
	public void FindPath() {
		while(!openingSet.isEmpty()) {
//			System.out.println("======================");
			//take the most priority node then move node from opening set to closing set
			int node = openingSet.get(0);
//			System.out.println("get node:"+ node);
			if(node==DESTINATION) {
				System.out.println("result: " + f[DESTINATION]);
				break;
			}
			closeingSet.add(node);
			openingSet.remove(openingSet.indexOf(node));
//			//debug*********
//			System.out.println("openingSet:");
//			for(int temp : openingSet) {
//				System.out.println(temp+" ");
//			}
//			System.out.println();
//			System.out.println("closingSet:");
//			for(int temp : closeingSet) {
//				System.out.print(temp+" ");
//			}
//			System.out.println();
//			//>***********
			//find other node that connect to current node
			for(int j=0; j<n; j++) {
				if(matrix[node][j]>0) {
					//if node belong openingset then update other connectable node if can find a shorter path
					//update that node lead to j
					
					//if node belong openingset then update other connectable node if can find a shorter path
					//then move those connectable node from closingSet to openingSet
					//update that node lead to j
					
					//if the connectable mode doesnt belong to either openingSet or closingSet then
					//add it to openingSet
						if((g[node] + matrix[node][j]) < g[j]) {
							g[j] = g[node] + matrix[node][j];
							f[j] = g[j] + heuristic[j];
							
							prev[j] = node;
//							System.out.println("*\t use "+node+" to maximize "+j);
							
							if(closeingSet.contains(j)) {
								AddToOpeningSetWithPriority(j);
								closeingSet.remove(closeingSet.indexOf(j));
//								System.out.println("*\t move "+j+ " from close to open");
							}else if(!openingSet.contains(j)) {
								AddToOpeningSetWithPriority(j);
//								System.out.println("*\t add "+j+" to open");
							}

						}
						
				}
				
			}
//			//debug*********
//			System.out.println("openingSet:");
//			for(int temp : openingSet) {
//				System.out.print(temp+" ");
//			}
//			System.out.println();
//			System.out.println("closingSet:");
//			for(int temp : closeingSet) {
//				System.out.print(temp+" ");
//			}
//			System.out.println();
//			int i=0;
//			for(int temp:g) {
//				System.out.print(i+":"+temp+"  ");
//				i++;
//			}
//			System.out.println();
//			i=0;
//			for(int temp:f) {
//				System.out.print(i+":"+temp+"  ");
//				i++;
//			}
//			System.out.println();
//			//>***********

			
		}
	}
	private void AddToOpeningSetWithPriority(Integer j) {
		int i=0;
		if(!openingSet.isEmpty()) {
			while(i<openingSet.size() && f[openingSet.get(i)]<f[j] ) {
				i++;
			}
		}
		openingSet.add(i,j);
	}
	public void PrintPath() {
		for(int temp:prev) {
			System.out.print(temp+" ");
		}
		int temp = DESTINATION;
		System.out.println();
		while(temp!=SOURCE) {
			System.out.print(temp+"<-");
			temp = prev[temp];
		}
		System.out.println(SOURCE);
	}
}	
