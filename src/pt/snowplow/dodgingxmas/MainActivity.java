package pt.snowplow.dodgingxmas;

import android.app.Activity;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private MainView mainView;
	private GameThread gameThread;
	
	public static TiltSensor tiltSensor = new TiltSensor();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.gameThread = new GameThread();
		this.mainView = (MainView) findViewById(R.id.main_view);
		this.mainView.setGameThread(gameThread);

		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		tiltSensor.enable(sensorManager);
	}

	@Override
	protected void onDestroy() {
		tiltSensor.disable();
		try {
			this.gameThread.setRunning(false);
			this.gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

}