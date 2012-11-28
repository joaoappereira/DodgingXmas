package pt.snowplow.dodgingxmas.managers;

import android.graphics.Rect;
import android.util.Pair;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import pt.snowplow.dodgingxmas.GameThread;
import pt.snowplow.dodgingxmas.gameobjects.DodgerObject;
import pt.snowplow.dodgingxmas.gameobjects.PhysicsComponent;
import pt.snowplow.dodgingxmas.gameobjects.VisualComponent;
import pt.snowplow.dodgingxmas.gameobjects.XmasGiftObject;
import pt.snowplow.framework.Logger;

public class ObstacleManager {
	
	private static final int BOX_Y_VELOCITY_MAX = 20;
	private static final int BOX_Y_VELOCITY_MIN = 2;
	private static final int BOX_MAX = 5;

	private final Random random = new Random();

	private final GameThread gameThread;

	public List<XmasGiftObject> obstacles = new LinkedList<XmasGiftObject> ();

	private int dodgedObstacles = 0;

	
	public ObstacleManager( GameThread gameThread ) {
		this.gameThread = gameThread;
	}

	public void surfaceChanged(int format, int width, int height) {
		Logger.log( "ObstacleManager . surfaceChanged() - creating " + BOX_MAX + " boxes" );
		restart( width, height );
	}
	
	private void resetObstacle(XmasGiftObject xgo, int canvasWidth, int canvasHeight) {
		VisualComponent visualComponent = ( VisualComponent ) xgo.getComponent(VisualComponent.class);
		Pair<Float,Float> position = this.getRandomPosition( visualComponent.getWidth(), canvasWidth );
		Pair<Float,Float> velocity = this.getRandomVelocity();
				
		PhysicsComponent pc = (PhysicsComponent) xgo.getComponent(PhysicsComponent.class);
		pc.setPosition(position);
		pc.setVelocity(velocity);
	}
	
	private Pair<Float,Float> getRandomPosition(int spriteWidth, int screenWidth) {
		float x = random.nextInt(screenWidth - spriteWidth);
		if(x < spriteWidth) { x = spriteWidth; }
		float y = 0;
		return new Pair<Float,Float>(x,y);
	}
	
	private Pair<Float,Float> getRandomVelocity() {
		float vx = 0.0f;
		float vy = random.nextInt(BOX_Y_VELOCITY_MAX);
		if(vy < BOX_Y_VELOCITY_MIN) {
			vy = BOX_Y_VELOCITY_MIN;
		}
		return new Pair<Float,Float>(vx,vy);
	}
	
	public void compute( int canvasWidth, int canvasHeight ) {
		for(XmasGiftObject obstacle : obstacles) {
			PhysicsComponent pc = (PhysicsComponent) obstacle.getComponent(PhysicsComponent.class);

			if ( collision( obstacle ) ) {
				gameThread.gameOver();
			} else
			// if not, update obstacle position
			if(pc.getPosition().second > canvasHeight) {
				dodgedObstacles++;
				resetObstacle(obstacle, canvasWidth, canvasHeight);
			}
		}
	}

	public void restart( int width, int height ) {
		dodgedObstacles = 0;

		if ( obstacles.isEmpty() ) {
			for ( int i = 0; i < BOX_MAX; i++ ) {
				XmasGiftObject xmasGiftObject = new XmasGiftObject( gameThread.getBitmapLoader().getBox() );

				gameThread.getGameObjectPool().register(xmasGiftObject);
				obstacles.add( xmasGiftObject );
			}
		}
		for ( XmasGiftObject obstacle : obstacles ) {
			resetObstacle( obstacle, width, height );
		}
	}

	private boolean collision( XmasGiftObject obstacle ) {
		DodgerObject dodgerObject = gameThread.getDodgerObject();
		VisualComponent dodgerVisualComponent = ( VisualComponent ) dodgerObject.getComponent( VisualComponent.class );
		PhysicsComponent dodgerPhysicsComponent = ( PhysicsComponent ) dodgerObject.getComponent( PhysicsComponent.class );
		Pair< Float, Float > dodgerPosition = dodgerPhysicsComponent.getPosition();
		dodgerVisualComponent.getWidth();
		dodgerVisualComponent.getHeight();

		VisualComponent obstacleVisualComponent = ( VisualComponent ) obstacle.getComponent( VisualComponent.class );
		PhysicsComponent obstaclePhysicsComponent = ( PhysicsComponent ) obstacle.getComponent( PhysicsComponent.class );
		Pair< Float, Float > obstaclePosition = obstaclePhysicsComponent.getPosition();
		obstacleVisualComponent.getWidth();
		obstacleVisualComponent.getHeight();

		int obstacleUpper = obstaclePosition.second.intValue();
		int obstacleLeft = obstaclePosition.first.intValue();
		int obstacleRight = ( int ) ( obstaclePosition.first + obstacleVisualComponent.getWidth() );
		int obstacleBottom = ( int ) ( obstaclePosition.second + obstacleVisualComponent.getHeight() );
		Rect obstacleRectangle = new Rect( obstacleLeft, obstacleUpper, obstacleRight, obstacleBottom );

		int dodgerUpper = dodgerPosition.second.intValue();
		int dodgerLeft = dodgerPosition.first.intValue();
		int dodgerRight = ( int ) ( dodgerPosition.first + dodgerVisualComponent.getWidth() );
		int dodgerBottom = ( int ) ( dodgerPosition.second + dodgerVisualComponent.getHeight() );
		Rect dodgerRectangle = new Rect( dodgerLeft, dodgerUpper, dodgerRight, dodgerBottom );

		return Rect.intersects( obstacleRectangle, dodgerRectangle );
	}

	public int getDodgedObstacles() {
		return dodgedObstacles;
	}
}
