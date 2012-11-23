package pt.snowplow.dodgingxmas;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class Box {

	private static final int BOX_Y_VELOCITY_MAX = 15;
	private static final int BOX_Y_VELOCITY_MIN = 5;

	private int x;
	private int y;
	private int yVelocity;
	private Bitmap bitmap;
	private final int screenWidth;

	public Box(Context context, int screenWidth) {
		this.screenWidth = screenWidth;
		bitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.box)).getBitmap();
		reset();
	}

	public void reset() {
		x = new Random().nextInt(screenWidth - bitmap.getWidth());
		y = 0;
		yVelocity = new Random().nextInt(BOX_Y_VELOCITY_MAX);
		if(yVelocity < BOX_Y_VELOCITY_MIN) {
			yVelocity = BOX_Y_VELOCITY_MIN;
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getYvelocity() {
		return yVelocity;
	}

	public void setYvelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
