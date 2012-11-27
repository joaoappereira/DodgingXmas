package pt.snowplow.framework;

import android.util.Pair;

public class VisualComponent extends GameObjectComponent {
	
	private Object sprite;
	private Pair<Float,Float> position;

	public void init(Object sprite) {
		this.sprite = sprite;
		this.position = new Pair<Float, Float> (0.0f, 0.0f);
	}
	
	@Override
	public void update() {
	}
	
	public Pair<Float, Float> getPosition() {
		return position;
	}

	public void setPosition(Pair<Float, Float> position) {
		this.position = position;
	}
	
	public Object getSprite() {
		return this.sprite;
	}
}
