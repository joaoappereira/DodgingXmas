package pt.snowplow.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import pt.snowplow.dodgingxmas.R;

public class BitmapLoader {

	private final Context context;

	private Bitmap box = null;

	private Bitmap ball = null;

	public BitmapLoader( Context context ) {
		this.context = context;

	}

	public Bitmap getBox() {
		if ( box == null ) {
			box = ( (BitmapDrawable ) context.getResources().getDrawable(R.drawable.box ) ).getBitmap();
		}
		return box;
	}

	public Bitmap getBall() {
		if ( ball == null ) {
			ball = ( ( BitmapDrawable ) context.getResources().getDrawable( R.drawable.ball ) ).getBitmap();
		}
		return ball;
	}
}
