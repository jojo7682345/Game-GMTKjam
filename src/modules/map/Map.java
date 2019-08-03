package modules.map;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.kernel.CoreEngine;
import core.math.Vec2f;
import core.math.Vec3f;
import core.scene.Scene;
import core.texturing.Sprite;
import modules.components.Pathfinding;
import modules.pathfinding.Node;
import modules.pathfinding.SquareGraph;

public class Map {

	public static final int	MAP_SIZE	= 16;
	public static final int	TILE_SIZE	= 8;

	private SquareGraph graph = new SquareGraph(MAP_SIZE);

	private Tile[][] tiles = new Tile[MAP_SIZE][MAP_SIZE];

	private static Map instance;

	public static Map getInstance() {
		if (instance == null) {
			instance = new Map();
		}
		return instance;
	}

	public Map() {
		readFile();
		compileMap();
	}

	float						timer		= 1;
	private List<Pathfinding>	pathfinders	= new ArrayList<>();

	public void update() {
		compileMap();
		timer -= CoreEngine.deltaTime / 1000;
		if (timer <= 0) {
			for (Pathfinding path : pathfinders) {
				Vec2f to = new Vec2f();
				Vec3f pos = path.getWorldTransform().getTranslation();
				Vec2f targ = path.getTarget();
				if (graph.isInsideMap(new Point((int) (pos.getX() / TILE_SIZE), (int) (pos.getY() / TILE_SIZE)))) {

					Point p = compute((int) Math.round((pos.getX() / TILE_SIZE)), (int) Math.round((pos.getY() / TILE_SIZE)), (int) Math.max(Math.min(targ.getX(), MAP_SIZE), 0), (int) Math.max(Math.min(targ
					        .getY(), MAP_SIZE), 0));
					float deltaX = (float) (p.getX()*TILE_SIZE-pos.getX());
					float deltaY = (float) (p.getY()*TILE_SIZE-pos.getY());
					to.setX((float) (pos.getX() + deltaX*path.getSpeed()*Math.cos(Math.atan2(deltaY,deltaX))));
					to.setY((float) (pos.getY() + deltaY*path.getSpeed()*Math.sin(Math.atan2(deltaY,deltaX))));
					System.out.println(pos);
				} else {
					if (targ.sub(new Vec2f(pos.getX(), pos.getY())).length() != 0) {
						targ = targ.normalize();
						to.setX(targ.getX() * path.getSpeed());
						to.setX(targ.getY() * path.getSpeed());
					}
				}
				path.getWorldTransform().setTranslation(to.getX(), to.getY(), pos.getZ());
			}
			timer = 1f;
		}
	}

	public Point compute(int x, int y, int targX, int targY) {
		graph.setStartPosition(new Point(x, y));
		graph.setTargetPosition(new Point(targX, targY));
		List<Node> p = graph.executeAStar();
		if (p == null) {
			return new Point(x, y);
		}
		return p.get(Math.min(1, p.size() - 1)).getPosition();
	}

	public void compileMap() {
		for (int x = 0; x < MAP_SIZE; x++) {
			for (int y = 0; y < MAP_SIZE; y++) {
				Node node = new Node(x, y, tiles[x][y].getType().getType());
				graph.setMapCell(new Point(x, y), node);
			}
		}
	}

	public void addPathfinder(Pathfinding pathfinding) {
		pathfinders.add(pathfinding);
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
			int y = MAP_SIZE - 1;
			HashMap<String, String> variables = new HashMap<>();
			HashMap<String, Tile.Type> types = new HashMap<>();
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(":v:")) {
					String data = line.split(":")[2];
					String[] var = data.split("=");
					variables.put(var[0].trim().replace(":", "").replace(" ", ""), var[1].trim());
					types.put(var[0].trim().replace(":", "").replace(" ", ""), Tile.Type.fromType(var[2].trim()));
					continue;
				}
				String[] row = line.split(" ");
				for (String t : row) {
					String[] data = t.split(":");
					Tile tile = new Tile(Sprite.load(variables.get(data[0])), types.get(data[0]), x, y, Integer.parseInt(data[1]));
					tiles[x][y] = tile;
					Scene.addObject(tile);
					x++;
				}
				x = 0;
				y--;
			}
			reader.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
