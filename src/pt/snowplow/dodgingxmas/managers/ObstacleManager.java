package pt.snowplow.dodgingxmas.managers;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pt.snowplow.dodgingxmas.gameobjects.XmasGiftObject;
import pt.snowplow.framework.GameObjectPool;
import pt.snowplow.framework.PhysicsComponent;
import android.util.Pair;

public class ObstacleManager {
	
	private static final int BOX_Y_VELOCITY_MAX = 15;
	private static final int BOX_Y_VELOCITY_MIN = 10;
	private static final int BOX_MAX = 5;
	
	private final GameObjectPool gameObjectPool;
	public List<XmasGiftObject> obstacles = new LinkedList<XmasGiftObject> ();

	private int dodgedObstacles = 0;
	private int screenWidth;
	private int screenHeight;
	private int spriteWidth;
	
	public ObstacleManager(GameObjectPool pool) {	
		this.gameObjectPool = pool;
		for(int i = 0; i < BOX_MAX; i++) {
			XmasGiftObject xgo = new XmasGiftObject();
			this.gameObjectPool.register(xgo);
			obstacles.add(xgo);
		}
	}
	
	public int getDodgedObstacles() {
		return this.dodgedObstacles;
	}

	public void init(Object sprite, int spriteWidth, int screenWidth, int screenHeight) {
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.spriteWidth = spriteWidth;
		
		for(XmasGiftObject xgo : obstacles) {
			xgo.init(sprite);
			this.resetObstacle(xgo);
		}
	}
	
	private void resetObstacle(XmasGiftObject xgo) {
		Pair<Float,Float> position = this.getRandomPosition(spriteWidth, screenWidth);
		Pair<Float,Float> velocity = this.getRandomVelocity(screenHeight);
				
		PhysicsComponent pc = (PhysicsComponent) xgo.getComponent(PhysicsComponent.class);
		pc.setPosition(position);
		pc.setVelocity(velocity);
	}
	
	private Pair<Float,Float> getRandomPosition(int spriteWidth, int screenWidth) {
		float x = new Random().nextInt(screenWidth - spriteWidth);
		if(x < spriteWidth) { x = spriteWidth; }
		float y = 0;
		return new Pair<Float,Float>(x,y);
	}
	
	private Pair<Float,Float> getRandomVelocity(int screenHeight) {
		float vx = 0.0f;
		float vy = new Random().nextInt(BOX_Y_VELOCITY_MAX);
		if(vy < BOX_Y_VELOCITY_MIN) {
			vy = BOX_Y_VELOCITY_MIN;
		}
		return new Pair<Float,Float>(vx,vy);
	}
	
	public void compute() {
		for(XmasGiftObject obstacle : obstacles) {
			PhysicsComponent pc = (PhysicsComponent) obstacle.getComponent(PhysicsComponent.class);
			if(pc.getPosition().second > this.screenHeight) {
				dodgedObstacles++;
				this.resetObstacle(obstacle);
			}
		}
	}

}
