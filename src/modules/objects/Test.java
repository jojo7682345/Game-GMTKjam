package modules.objects;

import core.kernel.CoreEngine;
import core.kernel.Input;
import core.scene.Layer;
import core.texturing.Sprite;
import core.utils.Audio;

public class Test extends SpriteObject {

	private Layer	layer;

	public Test() {
		super(Sprite.load("titleScreen"));
	}

	@Override public void onCreate() {
		scale();
		setIndex(2);
		setFramerate(20);
	}

	public void render() {
		
	}
	
	float delay = 0f;

	public void update() {
		super.update();
		if (!Input.getInstance().getKeysHolding().isEmpty() && delay < 0) {
			Audio.playSound("snd_reload", (int[] pointers) -> {
				Audio.setGain(1f, pointers); // Any of these are optional
				Audio.setPitch(1.2f, pointers);
				Audio.setLooping(false, pointers);
			});
			delay = 0.2f;
		}
		delay -= CoreEngine.deltaTime / 1000;
	}

}
