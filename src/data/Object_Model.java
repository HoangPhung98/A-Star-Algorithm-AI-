package data;

public class Object_Model {
	private int index;
	private String name;
	private int heuristic;
	private int[][] connectableNode;

	public void Object_Model(int index, String name, int hueristic, int[][] connectableNode) {
		this.index = index;
		this.name = name;
		this.heuristic = heuristic;
		this.connectableNode = connectableNode;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHeuristic() {
		return heuristic;
	}
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	public int[][] getConnectableNode() {
		return connectableNode;
	}
	public void setConnectableNode(int[][] connectableNode) {
		this.connectableNode = connectableNode;
	}
	
	
	
}
