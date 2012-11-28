package pt.snowplow.dodgingxmas.gameobjects;

import android.util.Pair;
import pt.snowplow.framework.GameObject;
import pt.snowplow.framework.PhysicsComponent;
import pt.snowplow.framework.TiltComponent;
import pt.snowplow.framework.VisualComponent;

public class DodgerObject extends GameObject {

	public int screenWidth;
	public int dodgerWidth;
	
	public DodgerObject() {
		VisualComponent vc = new VisualComponent();
		addComponent(VisualComponent.class, vc);
		
		PhysicsComponent pc = new PhysicsComponent();
		addComponent(PhysicsComponent.class, pc);
		
		TiltComponent tc = new TiltComponent();
		addComponent(TiltComponent.class, tc);
	}
	
	public void init(Object sprite, int screenWidth, int screenHeight, int spriteHeight, int spriteWidth) {
		this.screenWidth = screenWidth;
		this.dodgerWidth = spriteWidth;
		VisualComponent vc = (VisualComponent) getComponent(VisualComponent.class);
		vc.init(sprite);
		float ballX = screenWidth/2;
		float ballY = screenHeight - spriteHeight;
		Pair<Float,Float> ballPos = new Pair<Float,Float>(ballX,ballY);
		PhysicsComponent pc = (PhysicsComponent) getComponent(PhysicsComponent.class);
		pc.setPosition(ballPos);
	}
	
	@Override
	public void update() {
		super.update();
		TiltComponent tc = (TiltComponent) getComponent(TiltComponent.class);
		PhysicsComponent pc = (PhysicsComponent) getComponent(PhysicsComponent.class);
		float[] tilt = tc.getTilt();
		Pair<Float,Float> curPos = pc.getPosition();
		
		float newX = curPos.first + tilt[2];
		if( (newX > screenWidth - dodgerWidth) || (newX < 0) ) {
			newX = curPos.first;
		}
		
		Pair<Float,Float> newPos = new Pair<Float, Float>(newX, curPos.second);
		pc.setPosition(newPos);
		VisualComponent vc = (VisualComponent) this.getComponent(VisualComponent.class);
		vc.setPosition(pc.getPosition());
	}
	
}
