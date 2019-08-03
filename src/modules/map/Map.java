package modules.map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import core.kernel.CoreEngine;
import core.kernel.Window;
import core.scene.Layer;
import core.scene.Scene;
import core.texturing.Sprite;
import modules.pathfinding.Node;
import modules.pathfinding.SquareGraph;

public class Map {

	public static final int	MAP_SIZE	= 16;
	public static final int	TILE_SIZE	= 9;

	private SquareGraph graph = new SquareGraph(MAP_SIZE);

	private Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];

	private static Map instance;
	
	private int x= 0;
	private int y= 0;
	
	public static Map getInstance() {
		if(instance==null) {
			instance= new Map();
		}
		return instance;
	}
	
	public Map() {
		readFile();
		compileMap();
	}
	
	float timer = 1;
	public void update() {
		compileMap();
		timer-=CoreEngine.deltaTime/1000;
		if(timer <= 0) {
			Point p = compute(x/TILE_SIZE,y/TILE_SIZE,15,15);
			x = (int) p.getX()*TILE_SIZE;
			y = (int) p.getY()*TILE_SIZE;
			timer = .5f;
		}
	}
	
	public Point compute(int x, int y, int targX, int targY) {
		graph.setStartPosition(new Point(x,y));
		graph.setTargetPosition(new Point(targX,targY));
		List<Node> p = graph.executeAStar();
		return p.get(Math.min(1, p.size()-1)).getPosition();
	}
	
	public void compileMap() {
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {
				Node node = new Node(x, y, tiles[x][y].getType().getType());
				graph.setMapCell(new Point(x, y), node);
			}
		}
	}

	public SquareGraph getGraph() {
		return null;
	}

	public void readFile() {
		File map = new File("./res/maps/mainMap.map");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(map));
			String line = "";
			int x = 0;
			int y = MAP_SIZE-1;
			HashMap<String,String> variables = new HashMap<>();
			HashMap<String,Tile.Type> types = new HashMap<>();
			while ((line = reader.readLine()) != null) {
				if(line.startsWith(":v:")) {
					String data = line.split(":")[2];
					String[] var = data.split("=");
					variables.put(var[0].trim().replace(":", "").replace(" ", ""),var[1].trim());
					types.put(var[0].trim().replace(":", "").replace(" ", ""),Tile.Type.fromType(var[2].trim()));
					continue;
				}
				String[] row = line.split(" ");
				for(String t : row) {
					String[] data= t.split(":");
					Tile tile = new Tile(Sprite.load(variables.get(data[0])), types.get(data[0]),x,y,Integer.parseInt(data[1]));
					tiles[x][y] = tile;
					Scene.addObject(tile);
					x++;
				}
				x=0;
				y--;
			}
			reader.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
	Layer layer = new Layer(1);
	
	public void render() {
		layer.draw((Layer layer)->{
			//layer.drawRectangle(x+Window.getInstance().viewPortWidth/2, Window.getInstance().viewPortHeight/2-y, 1, 1);
			layer.drawRectangle(x+Window.getInstance().viewPortWidth/2-1, Window.getInstance().viewPortHeight/2-y-1, 2, 2);
		});
	}
	
}
