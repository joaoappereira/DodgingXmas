package pt.snowplow.dodgingxmas.gameobjects;

import android.graphics.Bitmap;
import android.util.Pair;

public class DodgerObject extends GameObject {

	private int width;

	private int height;

	public DodgerObject( Bitmap sprite, int width, int height ) {
		this.width = width;
		this.height = height;

		addComponent(VisualComponent.class, new VisualComponent( sprite ) );
		addComponent(PhysicsComponent.class, new PhysicsComponent() );
		addComponent(TiltComponent.class, new TiltComponent() );
	}
	
	@Override
	public void update( int canvasWidth, int canvasHeight ) {
		super.update( canvasWidth, canvasHeight );

		TiltComponent tc = (TiltComponent) getComponent(TiltComponent.class);
		PhysicsComponent pc = (PhysicsComponent) getComponent(PhysicsComponent.class);
		float[] tilt = tc.getTilt();
		Pair<Float,Float> curPos = pc.getPosition();
		
		float newX = curPos.first + tilt[2] * 20;
		if( (newX > canvasWidth - width) || (newX < 0) ) {
			newX = curPos.first;
		}
		
		Pair<Float,Float> newPos = new Pair<Float, Float>(newX, curPos.second);
		pc.setPosition(newPos);
		VisualComponent vc = (VisualComponent) this.getComponent(VisualComponent.class);
		vc.setPosition(pc.getPosition());
	}
	
}
