package pt.snowplow.dodgingxmas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import pt.snowplow.dodgingxmas.managers.GameObjectPool;
import pt.snowplow.dodgingxmas.managers.ObstacleManager;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

	private final GameThread gameThread;

	private final Paint paintScore = new Paint();

	public MainView( Context context, AttributeSet attributeSet ) {
		super( context, attributeSet );
		this.gameThread = new GameThread( getHolder(), this );
		this.getHolder().addCallback( this );
		this.setFocusable( true );

		paintScore.setAntiAlias(true);
		paintScore.setTextSize(30);
		paintScore.setColor(Color.WHITE);
		paintScore.setTextAlign(Paint.Align.LEFT);
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
		gameThread.surfaceChanged(surfaceHolder, format, width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		gameThread.setRunning( true );
		gameThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		boolean retry = true;
		while ( retry ) {
			try {
				gameThread.setRunning( false );
				gameThread.join();
				retry = false;
			} catch ( InterruptedException e ) {
				//
			}
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		GameObjectPool gameObjectPool = gameThread.getGameObjectPool();
		if ( gameObjectPool != null ) {
			gameObjectPool.draw( canvas );
		}

		ObstacleManager obstacleManager = gameThread.getObstacleManager();
		canvas.drawText( "Objects dodged: " + obstacleManager.getDodgedObstacles(), 20, 30, paintScore);
	}

}
