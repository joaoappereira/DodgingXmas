package pt.snowplow.framework;

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
	
	public void update() {
		for(GameObjectComponent c : components.values()) {
			c.update();
		}
	}
}
