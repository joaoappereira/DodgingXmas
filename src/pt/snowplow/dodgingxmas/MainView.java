package pt.snowplow.dodgingxmas;

import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MainView extends ImageView {
	
	private Handler handler;
	private final int FRAME_RATE = 30;
	private final Context context;

	// BALL
	private int ballX;
	private int ballY;
	private int ballXvelocity = 0;
	private boolean firstBallRun = true;
	private int rightEdge;
	private static final int leftEdge = 0;
	private final Bitmap ball;

	// BOXES
	private List<Box> boxes;
	private static final int BOX_MAX = 3;

	public MainView(Context context, AttributeSet attrs)  {
		super(context, attrs);
		handler = new Handler();
		this.context = context;
		ball = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.ball)).getBitmap();
		boxes = new LinkedList<Box>();
	}

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			invalidate();
		}
	};

	public void setBallXvelocity(int ballXvelocity) {
		this.ballXvelocity = ballXvelocity;
	}

	private boolean isObjPresent(Bitmap obj, int objX, int objY, int x, int y) {
		return (objX <= x && (objX + obj.getWidth()) >= x)
				|| (objY >= y && (objY - obj.getHeight()) <= y) ? true : false;
	}

	private boolean isBallBoxCollision() {
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
	}

	private void drawBox(Canvas c, Box b) {
		// inside screen condition
		if (b.getY() <= getHeight()) {
			b.setY(b.getY() + b.getYvelocity());
		}

		// off screen condition
		if (b.getY() > getHeight()) {
			b.reset();
		} else {
			c.drawBitmap(b.getBitmap(), b.getX(), b.getY(), null);
		}
	}

	private void drawBoxes(Canvas c) {
		if(boxes.isEmpty()) {
			for(int i = 0; i < BOX_MAX; i++) {
				boxes.add(new Box(context, getWidth()));
			}
		}

		for(Box b : boxes) {
			drawBox(c, b);
		}
	}

	private void drawBall(Canvas c) {
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
	}
	
	private void resetGame() {
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
	}

	protected void onDraw(Canvas c) {
		if(isBallBoxCollision() && !firstBallRun && !boxes.isEmpty()) {
			drawBall(c);
			drawBoxes(c);
			gameEnd();
		} else {
			drawBall(c);
			drawBoxes(c);
			handler.postDelayed(r, FRAME_RATE);
		}
	}

}
