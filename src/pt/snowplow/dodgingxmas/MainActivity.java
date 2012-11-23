package pt.snowplow.dodgingxmas;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends Activity implements SensorEventListener {

	private MainView mainView;

	private SensorManager sensorManager;
	private Sensor sensorRotationVector;
	private final float[] rotationMatrix = new float[16];
	private final int SPEED = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainView = (MainView) findViewById(R.id.main_view);
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensorRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		initializeRotationMatrix();
		enableSensor();
	}

	// SENSOR
	private void initializeRotationMatrix() {
		rotationMatrix[0] = 1;
		rotationMatrix[1] = 1;
		rotationMatrix[2] = 1;
		rotationMatrix[3] = 1;
	}

	private void enableSensor() {
		sensorManager.registerListener(this, sensorRotationVector, SensorManager.SENSOR_DELAY_GAME);
	}

	public void disableSensor() {
		sensorManager.unregisterListener(this);	
	}

	@Override
	public final void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			SensorManager.getRotationMatrixFromVector(rotationMatrix , event.values);
			int xVelocity = -SPEED * Math.round(rotationMatrix[2]*10);
			mainView.setBallXvelocity(xVelocity);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onPause() {
		disableSensor();
		super.onPause();
	}

	@Override
	protected void onStop() {
		disableSensor();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		disableSensor();
		super.onDestroy();
	}

}
