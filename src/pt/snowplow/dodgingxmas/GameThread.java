package pt.snowplow.dodgingxmas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Pair;
import android.view.SurfaceHolder;
import pt.snowplow.dodgingxmas.gameobjects.DodgerObject;
import pt.snowplow.dodgingxmas.gameobjects.PhysicsComponent;
import pt.snowplow.dodgingxmas.managers.GameObjectPool;
import pt.snowplow.dodgingxmas.managers.ObstacleManager;
import pt.snowplow.framework.BitmapLoader;
import pt.snowplow.framework.Logger;

public class GameThread extends Thread {

	private static final int MAX_FPS = 50;

	private static final int FRAME_PERIOD = 1000 / MAX_FPS;

	private static final int MAX_FRAME_SKIPS = 5;


	private final SurfaceHolder surfaceHolder;

	private final MainView mainView;

	private boolean running = true;

	private boolean paused = false;

	private final BitmapLoader bitmapLoader;

	private final GameObjectPool gameObjectPool = new GameObjectPool();

	private final ObstacleManager obstacleManager;

	private DodgerObject dodgerObject;


	public GameThread( SurfaceHolder surfaceHolder, MainView mainView ) {
		this.surfaceHolder = surfaceHolder;
		this.mainView = mainView;
		this.bitmapLoader = new BitmapLoader( mainView.getContext() );
		this.obstacleManager = new ObstacleManager( this );
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		long beginTime;
		long timeDiff;
		int framesSkipped;
		int sleepTime = 0;

		while ( running ) {
			if ( ! paused ) {
				Canvas canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas();
					synchronized ( surfaceHolder ) {
						beginTime = System.currentTimeMillis();
						framesSkipped = 0;

						// update game state
						gameObjectPool.update( canvas.getWidth(), canvas.getHeight() );
						obstacleManager.compute( canvas.getWidth(), canvas.getHeight() );

						// render updated game state
						mainView.onDraw( canvas );
						timeDiff = System.currentTimeMillis() - beginTime;

						sleepTime = ( int ) ( FRAME_PERIOD - timeDiff );
						if ( sleepTime > 0 ) {
							try {
								Logger.log( "run()", "sleeping for " + sleepTime );
								Thread.sleep( sleepTime );
							} catch ( InterruptedException e ) {
								//
							}
						}

//						while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
//							// update game state
//							gameObjectPool.update( canvas.getWidth(), canvas.getHeight() );
//							obstacleManager.compute( canvas.getWidth(), canvas.getHeight() );
//
//							sleepTime += FRAME_PERIOD;
//							framesSkipped++;
//						}

					}
				} finally {
					if ( canvas != null ) {
						surfaceHolder.unlockCanvasAndPost( canvas );
					}
				}
			}
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
		obstacleManager.surfaceChanged(format, width, height);
		restart( width, height );
	}

	public void gameOver() {
		paused = true;

		( ( MainActivity ) mainView.getContext() ).runOnUiThread( new Runnable() {
			@Override
			public void run() {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( mainView.getContext() );
				alertDialogBuilder
						.setMessage(R.string.dialog_game_end)
						.setCancelable(false)
						.setPositiveButton(R.string.button_play_again, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								restart(mainView.getWidth(), mainView.getHeight());
							}
						})
						.setNegativeButton(R.string.button_exit, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								((MainActivity) mainView.getContext()).finish();
							}
						});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

	private void restart( int width, int height ) {
		paused = false;

		Bitmap ball = bitmapLoader.getBall();
		if ( dodgerObject == null ) {
			dodgerObject = new DodgerObject( ball, ball.getWidth(), ball.getHeight() );
			gameObjectPool.register( dodgerObject );
		}
		Pair<Float, Float> initPosition = new Pair<Float, Float>( ( float ) width / 2, ( float ) height - ball.getHeight() );
		( (PhysicsComponent) dodgerObject.getComponent( PhysicsComponent.class ) ).setPosition( initPosition );

		obstacleManager.restart( width, height );
	}

	public GameObjectPool getGameObjectPool() {
		return gameObjectPool;
	}

	public ObstacleManager getObstacleManager() {
		return obstacleManager;
	}

	public BitmapLoader getBitmapLoader() {
		return bitmapLoader;
	}

	public DodgerObject getDodgerObject() {
		return dodgerObject;
	}
}
