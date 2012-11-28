package pt.snowplow.dodgingxmas.gameobjects;

import pt.snowplow.dodgingxmas.MainActivity;

public class TiltComponent implements GameObjectComponent {

	private float[] tilt;
	
	public float[] getTilt() {
		return tilt;
	}
	
	@Override
	public void update() {
		this.tilt = MainActivity.tiltSensor.getTiltData();
	}
}
