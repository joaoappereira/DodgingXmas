package pt.snowplow.dodgingxmas;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class TiltSensor implements SensorEventListener {
	
	SensorManager sensorManager;
	private Sensor sensorAccelerometer;
	private Sensor sensorMagneticField;
	
	private float[] tiltData = {0, 0, 0};
	private float[] gravityData = {0, 0, 0};
	private float[] magneticData = {0, 0, 0};
	
	public void enable(SensorManager sensorManager) {
		this.sensorManager = sensorManager;
		this.sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		this.sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		enable();
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) { }

	public void onSensorChanged(SensorEvent event) {
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			System.arraycopy(event.values, 0, gravityData, 0, 3);
		} else {
			System.arraycopy(event.values, 0, magneticData, 0, 3);
		}

		float[] R={0,0,0,0,0,0,0,0,0};
		if(SensorManager.getRotationMatrix(R, null, gravityData, magneticData)) {
			SensorManager.getOrientation(R, tiltData);
		}
	}
	
	public float[] getTiltData() {
		return tiltData;
	}
	
	public void enable() {
		sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(this, sensorMagneticField, SensorManager.SENSOR_DELAY_GAME);
	}
	
	public void disable() {
		sensorManager.unregisterListener(this);
	}

}
