package pt.snowplow.dodgingxmas.gameobjects;

import android.util.Pair;

public class PhysicsComponent implements GameObjectComponent {

	private static final float PHYSICS_STEP = 1.0f;
	
	private Pair<Float,Float> position;
	private Pair<Float,Float> velocity;
	
	public PhysicsComponent() {
		position = new Pair<Float, Float>(0.0f, 0.0f);
		velocity = new Pair<Float, Float>(0.0f, 0.0f);
	}
	
	@Override
	public void update() {
		float x = position.first + (velocity.first * PHYSICS_STEP);
		float y = position.second + (velocity.second * PHYSICS_STEP);
		this.position = new Pair<Float, Float>(x,y);
	}
	
	public Pair<Float, Float> getPosition() {
		return position;
	}

	public void setPosition(Pair<Float, Float> position) {
		this.position = position;
	}
	
	public Pair<Float, Float> getVelocity() {
		return velocity;
	}

	public void setVelocity(Pair<Float, Float> velocity) {
		this.velocity = velocity;
	}

}
