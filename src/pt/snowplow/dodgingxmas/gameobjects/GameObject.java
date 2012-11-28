package pt.snowplow.dodgingxmas.gameobjects;

import android.graphics.Canvas;
import java.util.HashMap;
import java.util.Map;

public abstract class GameObject {
	
	private Map<Object,GameObjectComponent> components = new HashMap<Object,GameObjectComponent>();
	
	public void addComponent(Object o,GameObjectComponent c) {
		this.components.put(o,c);
	}
	public GameObjectComponent getComponent(Object o) {
		return this.components.get(o);
	}
	
	public void update( int canvasWidth, int canvasHeight ) {
		for(GameObjectComponent c : components.values()) {
			c.update();
		}
	}

	public void draw( Canvas canvas ) {
		VisualComponent visualComponent = ( VisualComponent ) getComponent( VisualComponent.class );
		visualComponent.draw( canvas );
	}
}
