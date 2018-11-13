package algorithm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import graphic.Display;

public class Algorithm_ForUIDataInput {
	private final int ROW;
	private final int COLLUM;
	private final Point source;
	private final Point destination;
	
//	private int[][] color_matrix;
	public static Node[][] matrix;
	private int[] heuristic;
	private ArrayList<Node> openingSet;
	private ArrayList<Node> closeingSet;
	private final int NUMBER_OF_NODE;
	private final int INFINITY = 10000;
	private final int NUMBER_OF_NEAR_NODE = 8;
	private boolean DONE = false;
	public Algorithm_ForUIDataInput(int ROW,int COLLUM, Point source, Point destination) {
		this.ROW = ROW;
		this.COLLUM = COLLUM;
		NUMBER_OF_NODE = ROW * COLLUM - 2;
		
		this.source = source;
		this.destination = destination;
		System.out.println("sour:"+source.getX()+"-"+source.getY());
		System.out.println("des:"+destination.getX()+"-"+destination.getY());

		
		matrix = new Node[ROW][COLLUM];
		InitialNodeMatrix();
		matrix[(int)source.getX()][(int)source.getY()].g = 0;
		matrix[(int)destination.getX()][(int)destination.getY()].h = 0;

		
		
		openingSet = new ArrayList<>();
		openingSet.add(matrix[(int)source.getX()][(int)source.getY()]) ;
		closeingSet = new ArrayList<>();
	}
	//if node belong openingset then update other connectable node if can find a shorter path
	//update that node lead to j
	
	//if node belong openingset then update other connectable node if can find a shorter path
	//then move those connectable node from closingSet to openingSet
	//update that node lead to j
	
	//if the connectable mode doesnt belong to either openingSet or closingSet then
	//add it to openingSet
	public void FindPath() {
	
		if(!openingSet.isEmpty()) {
			Node node = openingSet.get(0);
			System.out.println("F:  "+node.coordinate.getX()+"-"+node.coordinate.getY()
			+"  "+node.g+" "+node.h+" "+node.f);
			if(node.coordinate.equals(destination)) {
				System.out.println("TIM THAY DUONG ROI");
				System.out.println("result: " + node.f);
				addResultPathToColor_Matrix();
				DONE = true;
			}
			if(!DONE) {
			closeingSet.add(node);
			openingSet.remove(openingSet.indexOf(node));
			if(Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()]!=1
				&& Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()]!=2)
				Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()] = 5;
			
			for(int j=0; j<NUMBER_OF_NEAR_NODE; j++) {
					
					Node nearNode = node.nearNodeList.get(j);
					if(nearNode!=null 
							&& Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()]!=3) {
						
						if((node.g + node.nearNode_Distance[j]) < nearNode.g) {
							nearNode.g = node.g + node.nearNode_Distance[j];
							nearNode.f = nearNode.g + nearNode.h;
						if(Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()]!=1
							&& Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()]!=2)
								Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()] = 4;
							
							nearNode.prevNode=node; //luu vet
							
							if(closeingSet.contains(nearNode)) {
								AddToOpeningSetWithPriority(nearNode);
								closeingSet.remove(closeingSet.indexOf(nearNode));
							}else if(!openingSet.contains(nearNode)) {
								AddToOpeningSetWithPriority(nearNode);
							}
						}
					}
			}
			}else {
				System.out.println("KHONG TIM THAY DUONG");
				DONE = true;
			}
		}

	}
	public void FindPath_Auto() {
		while(!DONE) {
			if(!openingSet.isEmpty()) {
				Node node = openingSet.get(0);
				if(node.coordinate.equals(destination)) {
					System.out.println("TIM THAY DUONG ROI");
					System.out.println("result: " + node.f);
					addResultPathToColor_Matrix();
					DONE = true;
					addResultPathToColor_Matrix();
					break;
				}
				closeingSet.add(node);
				openingSet.remove(openingSet.indexOf(node));
				if(Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()]!=1
						&& Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()]!=2)
							Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()] = 5;
				
				for(int j=0; j<NUMBER_OF_NEAR_NODE; j++) {
						
						Node nearNode = node.nearNodeList.get(j);
						if(nearNode!=null 
								&& Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()]!=3) {
							
							if((node.g + node.nearNode_Distance[j]) < nearNode.g) {
								
								nearNode.g = node.g + node.nearNode_Distance[j];
								nearNode.f = nearNode.g + nearNode.h;
								if(Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()]!=1
										&& Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()]!=2)
											Display.color_matrix[(int)nearNode.coordinate.getX()][(int)nearNode.coordinate.getY()] = 4;
								
								nearNode.prevNode=node; //luu vet
								
								if(closeingSet.contains(nearNode)) {
									AddToOpeningSetWithPriority(nearNode);
									closeingSet.remove(closeingSet.indexOf(nearNode));
								}else if(!openingSet.contains(nearNode)) {
									AddToOpeningSetWithPriority(nearNode);
								}
							}
						}
				}
				}else {
					System.out.println("KHONG TIM THAY DUONG");
					DONE = true;
				}
			}
	}
	private void debug_printOpeningSet() {
		for(int i=0; i< openingSet.size(); i++) {
			System.out.println(
					openingSet.get(i).coordinate.getX()+"-"+openingSet.get(i).coordinate.getY()
					+"-  "+openingSet.get(i).g
					+"-  "+openingSet.get(i).h
					+"-  "+openingSet.get(i).f);
		}
		System.out.println("===========");
	}
	private void debug_print_H_matrix() {
		for(int i=0; i< ROW; i++) {
			for(int j=0; j<COLLUM; j++) {
				System.out.print(matrix[i][j].h+"\t");
			}
			System.out.println();
		}
	}
	private void debug_print_near_node() {
		System.out.println("Near Node:");
		for(int k=0; k< ROW; k++) {
			for(int j=0 ;j<COLLUM; j++) {
				Node node = matrix[k][j];
				for(int i=0; i<node.nearNodeList.size(); i++) {
					if(node.nearNodeList.get(i)!=null)
					System.out.print(node.nearNodeList.get(i).h+" - ");
				}
				System.out.println();
			}
			System.out.println("============");
		}
		
	}
	public void resumFindPath() {
		FindPath();
	}
	private void AddToOpeningSetWithPriority(Node node) {
		int i=0;
		if(!openingSet.isEmpty()) {
			while(i<openingSet.size() && openingSet.get(i).f<node.f ) {
				i++;
			}
		}
		openingSet.add(i,node);
	}
	private void addResultPathToColor_Matrix() {
		Node node = matrix[(int)destination.getX()][(int)destination.getY()];
		node = node.prevNode;
		Node srcNode = matrix[(int)source.getX()][(int)source.getY()];
		while(!node.equals(srcNode)) {
			Display.color_matrix[(int)node.coordinate.getX()][(int)node.coordinate.getY()] = 6;
			node = node.prevNode;
		}
		Display.color_matrix[(int)srcNode.coordinate.getX()][(int)srcNode.coordinate.getY()] = 1;

	}
	private void InitialNodeMatrix_WithNearNodeArr() {
		//***corner
		//node[0][0]
		matrix[0][0].nearNodeList.add(null);
		matrix[0][0].nearNodeList.add(null);
		matrix[0][0].nearNodeList.add(matrix[0][1]);
		matrix[0][0].nearNodeList.add(matrix[1][1]);
		matrix[0][0].nearNodeList.add(matrix[1][0]);
		matrix[0][0].nearNodeList.add(null);
		matrix[0][0].nearNodeList.add(null);
		matrix[0][0].nearNodeList.add(null);

		//node[0][COLLUM]
		matrix[0][COLLUM-1].nearNodeList.add(null);
		matrix[0][COLLUM-1].nearNodeList.add(null);
		matrix[0][COLLUM-1].nearNodeList.add(null);
		matrix[0][COLLUM-1].nearNodeList.add(null);
		matrix[0][COLLUM-1].nearNodeList.add(matrix[1][COLLUM-1]);
		matrix[0][COLLUM-1].nearNodeList.add(matrix[1][COLLUM-2]);
		matrix[0][COLLUM-1].nearNodeList.add(matrix[0][COLLUM-2]);
		matrix[0][COLLUM-1].nearNodeList.add(null);
		//node[ROW][0]
		matrix[ROW-1][0].nearNodeList.add(matrix[ROW-2][0]);
		matrix[ROW-1][0].nearNodeList.add(matrix[ROW-2][1]);
		matrix[ROW-1][0].nearNodeList.add(matrix[ROW-1][1]);
		matrix[ROW-1][0].nearNodeList.add(null);
		matrix[ROW-1][0].nearNodeList.add(null);
		matrix[ROW-1][0].nearNodeList.add(null);
		matrix[ROW-1][0].nearNodeList.add(null);
		matrix[ROW-1][0].nearNodeList.add(null);

		//node[ROW][COLLUM]
		matrix[ROW-1][COLLUM-1].nearNodeList.add(matrix[ROW-2][COLLUM-1]);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(null);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(null);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(null);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(null);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(null);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(matrix[ROW-1][COLLUM-2]);
		matrix[ROW-1][COLLUM-1].nearNodeList.add(matrix[ROW-2][COLLUM-2]);
		
		//***edge
		for(int i=1; i<ROW-1; i++) {
			//left edge
			matrix[i][0].nearNodeList.add(matrix[i-1][0]);
			matrix[i][0].nearNodeList.add(matrix[i-1][1]);
			matrix[i][0].nearNodeList.add(matrix[i][1]);
			matrix[i][0].nearNodeList.add(matrix[i+1][1]);
			matrix[i][0].nearNodeList.add(matrix[i+1][0]);
			matrix[i][0].nearNodeList.add(null);
			matrix[i][0].nearNodeList.add(null);
			matrix[i][0].nearNodeList.add(null);


			//right edge
			matrix[i][COLLUM-1].nearNodeList.add(matrix[i-1][COLLUM-1]);
			matrix[i][COLLUM-1].nearNodeList.add(null);
			matrix[i][COLLUM-1].nearNodeList.add(null);
			matrix[i][COLLUM-1].nearNodeList.add(null);
			matrix[i][COLLUM-1].nearNodeList.add(matrix[i+1][COLLUM-1]);
			matrix[i][COLLUM-1].nearNodeList.add(matrix[i+1][COLLUM-2]);
			matrix[i][COLLUM-1].nearNodeList.add(matrix[i][COLLUM-2]);
			matrix[i][COLLUM-1].nearNodeList.add(matrix[i-1][COLLUM-2]);


			
		}
		for(int i=1; i<COLLUM-1; i++) {
			//top edge
			matrix[0][i].nearNodeList.add(null);
			matrix[0][i].nearNodeList.add(null);
			matrix[0][i].nearNodeList.add(matrix[0][i+1]);
			matrix[0][i].nearNodeList.add(matrix[1][i+1]);
			matrix[0][i].nearNodeList.add(matrix[1][i]);
			matrix[0][i].nearNodeList.add(matrix[1][i-1]);
			matrix[0][i].nearNodeList.add(matrix[0][i-1]);
			matrix[0][i].nearNodeList.add(null);

			//bottom edge
			
			matrix[ROW-1][i].nearNodeList.add(matrix[ROW-2][i]);
			matrix[ROW-1][i].nearNodeList.add(matrix[ROW-2][i+1]);
			matrix[ROW-1][i].nearNodeList.add(matrix[ROW-1][i+1]);
			matrix[ROW-1][i].nearNodeList.add(null);
			matrix[ROW-1][i].nearNodeList.add(null);
			matrix[ROW-1][i].nearNodeList.add(null);
			matrix[ROW-1][i].nearNodeList.add(matrix[ROW-1][i-1]);
			matrix[ROW-1][i].nearNodeList.add(matrix[ROW-2][i-1]);
		}
		//**others
		for(int i=1; i<ROW-1; i++) {
			for(int j=1; j<COLLUM-1; j++) {
				matrix[i][j].nearNodeList.add(matrix[i-1][j]); //top
				matrix[i][j].nearNodeList.add(matrix[i-1][j+1]);//top right
				matrix[i][j].nearNodeList.add(matrix[i][j+1]); //right
				matrix[i][j].nearNodeList.add(matrix[i+1][j+1]); //bottom right
				matrix[i][j].nearNodeList.add(matrix[i+1][j]); //bottom
				matrix[i][j].nearNodeList.add(matrix[i+1][j-1]); //bottom left
				matrix[i][j].nearNodeList.add(matrix[i][j-1]); //left
				matrix[i][j].nearNodeList.add(matrix[i-1][j-1]);//top left
			}
		}
	}
	private void InitialNodeMatrix() {
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COLLUM; j++) {
				int h = (int)(100*(Math.sqrt(Math.pow(destination.getX()-i, 2)+Math.pow(destination.getY()-j, 2))));
				matrix[i][j] = new Node(new Point(i,j),new ArrayList<Node>(),null,INFINITY, h, INFINITY+h);
			}
		}
		InitialNodeMatrix_WithNearNodeArr();

	}
}
