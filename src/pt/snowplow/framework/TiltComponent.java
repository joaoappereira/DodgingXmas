package pt.snowplow.framework;

import pt.snowplow.dodgingxmas.MainActivity;

public class TiltComponent extends GameObjectComponent {

	private float[] tilt;
	
	public float[] getTilt() {
		return tilt;
	}
	
	@Override
	public void update() {
		this.tilt = MainActivity.tiltSensor.getTiltData();
	}
}
