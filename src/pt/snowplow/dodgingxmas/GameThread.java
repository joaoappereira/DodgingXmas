package pt.snowplow.dodgingxmas;

import pt.snowplow.dodgingxmas.gameobjects.DodgerObject;
import pt.snowplow.dodgingxmas.managers.ObstacleManager;
import pt.snowplow.framework.GameObjectPool;
import android.os.Handler;

public class GameThread extends Thread {
	
	private static final long FRAME_RATE = 30;
	private Handler handler;
	private boolean running;
	private Runnable invalidator;
	
	private ObstacleManager obstacleManager;
	private GameObjectPool gameObjectPool;
	private DodgerObject dodgerObject;
	
	public GameThread() {
		this.handler = new Handler();
		this.gameObjectPool = new GameObjectPool();
		this.obstacleManager = new ObstacleManager(this.gameObjectPool);
		this.dodgerObject = new DodgerObject();
		gameObjectPool.register(this.dodgerObject);
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void setInvalidator(Runnable invalidator) {
		this.invalidator = invalidator;
	}
	
	@Override
	public void run() {
		while(running) {
			handler.postDelayed(invalidator, FRAME_RATE);
			this.gameObjectPool.update();
			this.obstacleManager.compute();
		}
		super.run();
	}
	
	public ObstacleManager getObstacleManager() {
		return obstacleManager;
	}

	public void setObstacleManager(ObstacleManager obstacleManager) {
		this.obstacleManager = obstacleManager;
	}

	public GameObjectPool getGameObjectPool() {
		return gameObjectPool;
	}

	public void setGameObjectPool(GameObjectPool gameObjectPool) {
		this.gameObjectPool = gameObjectPool;
	}

	public DodgerObject getDodgerObject() {
		return dodgerObject;
	}

	public void setDodgerObject(DodgerObject dodgerObject) {
		this.dodgerObject = dodgerObject;
	}

}
