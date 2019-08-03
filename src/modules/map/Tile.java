package modules.map;

import core.texturing.Sprite;
import modules.objects.SpriteObject;

public class Tile extends SpriteObject{

	private int x;
	private int y;

	public Tile(Sprite sprite,Type type, int x, int y, int rot) {
		super(sprite);
		this.setType(type);
		this.x =x;
		this.y = y;
		getWorldTransform().setTranslation(x*Map.TILE_SIZE,y*Map.TILE_SIZE,0);
		getWorldTransform().setRotation(0, 0, rot*90);
		scale();
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private Type type;
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type{
		GROUND("NORMAL"),
		WALL("OBSTACLE"),
		OBJECT("OBSTACLE");
		
		private String type;
		
		Type(String type){
			this.type = type;
		}
		
		public String getType() {
			return type;
		}
		
		public static Type fromType(String str) {
			for(Type type : values()) {
				if(type.type.equals(str)) {
					return type;
				}
			}
			return null;
		}
	}

	@Override public void onCreate() {
	}
	
}
