package algorithm;

import java.awt.Point;
import java.util.ArrayList;

public class Node {
	Point coordinate;
	ArrayList<Node> nearNodeList;
	static int[] nearNode_Distance = {10,14,10,14,10,14,10,14};
	public Node prevNode;
	public int g;
	public int h;
	public int f;
	public Node( Point coordinate,ArrayList<Node> nearNodeList ,Node prevNode, int g, int h, int f) {
		this.coordinate = coordinate;
		this.nearNodeList = nearNodeList;
		this.prevNode = prevNode;
		this.g = g;
		this.h = h;
		this.f = f;
	}
	
}
