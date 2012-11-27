package pt.snowplow.framework;

import java.util.LinkedList;
import java.util.List;

public class GameObjectPool {
	
	private List<GameObject> pool = new LinkedList<GameObject>();
	
	public void register(GameObject obj) {
		pool.add(obj);
	}
	
	public List<GameObject> getObjects() {
		return this.pool;
	}
	
	public void update() {
		for(GameObject go : pool) {
			go.update();
		}
	}
	
}
