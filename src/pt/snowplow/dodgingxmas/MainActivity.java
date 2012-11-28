package pt.snowplow.dodgingxmas;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends Activity {

	public static TiltSensor tiltSensor = new TiltSensor();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MainView mainView = new MainView( this, null );
		setContentView( mainView );

		SensorManager sensorManager = ( SensorManager ) getSystemService( SENSOR_SERVICE );
		tiltSensor.enable(sensorManager);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		tiltSensor.disable();
		super.onDestroy();
	}

}