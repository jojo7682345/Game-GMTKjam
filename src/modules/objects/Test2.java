package modules.objects;

import core.texturing.Sprite;

public class Test2 extends SpriteObject{

	public Test2() {
		super(Sprite.load("barrel"));
	}

	@Override public void onCreate() {
		scale();
		setIndex(0);
		setFramerate(0);
	}
	
	public void update() {
	}
	
}
