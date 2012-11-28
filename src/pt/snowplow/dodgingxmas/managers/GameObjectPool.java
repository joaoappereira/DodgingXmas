package pt.snowplow.dodgingxmas.managers;

import android.graphics.Canvas;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pt.snowplow.dodgingxmas.gameobjects.GameObject;

public class GameObjectPool {
	
	private List<GameObject> pool = Collections.synchronizedList( new LinkedList<GameObject>() );
	
	public void register(GameObject obj) {
		pool.add(obj);
	}


	public void update( int canvasWidth, int canvasHeight ) {
		for(GameObject go : pool) {
			go.update( canvasWidth, canvasHeight );
		}
	}

	public void draw( Canvas canvas ) {
		for ( GameObject gameObject : pool ) {
			gameObject.draw( canvas );
		}
	}

}
