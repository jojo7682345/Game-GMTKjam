package modules.objects;

import modules.pathfinding.SquareGraph;

import java.awt.Point;

import modules.pathfinding.Node;

public class Game {

	public static final int		MAP_SIZE	= 30;
	public static final float	UNIT		= 8;
	public static SquareGraph graph = new SquareGraph(MAP_SIZE);
	static {
		for(int x = 0; x < MAP_SIZE; x++) {
			for(int y = 0; y < MAP_SIZE; y++) {
				Node node = new Node(x,y,"NORMAL");
				graph.setMapCell(new Point(x,y), node);
			}
		}
	}
	
	
	public static SquareGraph getGraph() {
		return graph;
	}

}
