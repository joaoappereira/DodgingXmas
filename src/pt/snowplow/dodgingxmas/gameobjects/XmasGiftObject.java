package pt.snowplow.dodgingxmas.gameobjects;

import android.graphics.Bitmap;

public class XmasGiftObject extends GameObject {

	public XmasGiftObject( Bitmap sprite ) {
		addComponent(VisualComponent.class, new VisualComponent( sprite ) );
		addComponent(PhysicsComponent.class, new PhysicsComponent());
	}

	@Override
	public void update( int canvasWidth, int canvasHeight ) {
		super.update( canvasWidth, canvasHeight );

		PhysicsComponent pc = (PhysicsComponent) getComponent(PhysicsComponent.class);
		VisualComponent vc = (VisualComponent) this.getComponent(VisualComponent.class);
		vc.setPosition( pc.getPosition() );
	}

}
