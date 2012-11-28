package pt.snowplow.dodgingxmas.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Pair;

public class VisualComponent implements GameObjectComponent {
	
	private final Bitmap sprite;

	private Pair< Float, Float > position = new Pair<Float, Float> (0.0f, 0.0f);

	public VisualComponent( Bitmap sprite ) {
		this.sprite = sprite;
	}
	
	@Override
	public void update() {

	}

	public void setPosition(Pair<Float, Float> position) {
		this.position = position;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	public void draw( Canvas canvas ) {
		canvas.drawBitmap( sprite, position.first, position.second, null );
	}
}
