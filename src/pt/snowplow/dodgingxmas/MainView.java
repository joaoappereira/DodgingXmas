package pt.snowplow.dodgingxmas;

import pt.snowplow.dodgingxmas.gameobjects.DodgerObject;
import pt.snowplow.dodgingxmas.managers.ObstacleManager;
import pt.snowplow.framework.GameObject;
import pt.snowplow.framework.GameObjectPool;
import pt.snowplow.framework.VisualComponent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.ImageView;

public class MainView extends ImageView {
	
	private final Context context;
	
	//Game Objects
	private GameThread gameThread;
	private boolean init;
	
	//Score text
	private final Paint scoreTextPaint;

	public MainView(Context context, AttributeSet attrs)  {
		super(context, attrs);
		this.context = context;
		scoreTextPaint = new Paint();
		scoreTextPaint.setAntiAlias(true);
		scoreTextPaint.setTextSize(30);
		scoreTextPaint.setColor(Color.WHITE);
		scoreTextPaint.setTextAlign(Paint.Align.LEFT);
	}
	
	public void setGameThread(GameThread gameThread) {
		this.gameThread = gameThread;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if(this.gameThread != null) {
			if(!this.init) {
				this.init();
			} else {
				GameObjectPool gameObjectPool = this.gameThread.getGameObjectPool();
				if(gameObjectPool != null) {
					for(GameObject go : gameObjectPool.getObjects()){
						VisualComponent vc = (VisualComponent) go.getComponent(VisualComponent.class);
						Pair<Float,Float> pos = vc.getPosition();
						Bitmap b = (Bitmap) vc.getSprite();
						canvas.drawBitmap(b, pos.first, pos.second, null);
					}
				}
				ObstacleManager om = gameThread.getObstacleManager();
				if(om != null) {
					canvas.drawText("Objects dodged: " + om.getDodgedObstacles() , 20, 30, scoreTextPaint);
				}
			}
		}
		
		super.onDraw(canvas);
	}
	
	public void init() {
		if(gameThread != null) {
			ObstacleManager obstacleManager = gameThread.getObstacleManager();
			Bitmap box = ((BitmapDrawable) this.context.getResources().getDrawable(R.drawable.box)).getBitmap();
			obstacleManager.init(box, box.getWidth(), getWidth(), getHeight());
			Bitmap ball = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.ball)).getBitmap();
			DodgerObject dodgerObject = gameThread.getDodgerObject();
			if(dodgerObject != null) {
				dodgerObject.init(ball,getWidth(),getHeight(),ball.getHeight(), ball.getWidth());
			}
			Runnable invalidator = new Runnable() {
				public void run() {
					invalidate();
				}
			};
			this.gameThread.setInvalidator(invalidator);
			this.gameThread.setRunning(true);
			this.gameThread.start();
			this.init = true;
		}
	}

	
/*
	public void setBallXvelocity(int ballXvelocity) {
		this.ballXvelocity = ballXvelocity;
	}

	private boolean isObjPresent(Bitmap obj, int objX, int objY, int x, int y) {
		return (objX <= x && (objX + obj.getWidth()) >= x)
				|| (objY >= y && (objY - obj.getHeight()) <= y) ? true : false;
	}
*/
	/*private boolean isBallBoxCollision() {
		for(Box b : boxes) {
			int left = Math.max(b.getX(), ballX);
			int right = Math.min(b.getX() + b.getBitmap().getWidth(), ballX + ball.getWidth());
			int top = Math.max(b.getY() - b.getBitmap().getHeight(), ballY - ball.getHeight());
			int bottom = Math.min(b.getY(), ballY);

			for (int x = left; x < right; x++) {
				for (int y = top; y < bottom; y++) {
					if (isObjPresent(b.getBitmap(), b.getX(), b.getY(), x,y) && isObjPresent(ball, ballX, ballY, x,y)) {
						return true;
					}
				}
			}
		}
		return false;
	}*/


	/*private void drawBall(Canvas c) {
		if(firstBallRun) {
			ballX = getWidth()/2 - ball.getWidth()/2;
			ballY = getHeight() - ball.getHeight();
			rightEdge = getWidth() - ball.getWidth();
			firstBallRun = false;
		}

		// inside screen condition
		if (ballX <= rightEdge && ballX >= leftEdge) {
			ballX += ballXvelocity;
		}

		// off screen conditions
		if (ballX < leftEdge) {
			ballX = 0;
		}
		if (ballX > rightEdge) {
			ballX = rightEdge;
		}

		c.drawBitmap(ball, ballX, ballY, null);
	}*/
	
	/*private void resetGame() {
		boxes.clear();
		firstBallRun = true;
		handler.post(r);
	}

	private void gameEnd() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		
		alertDialogBuilder
		.setMessage(R.string.dialog_game_end)
		.setCancelable(false)
		.setPositiveButton(R.string.button_play_again, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
				resetGame();
			}
		})
		.setNegativeButton(R.string.button_exit, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				((MainActivity) context).finish();
			}
		});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}*/

	

}
