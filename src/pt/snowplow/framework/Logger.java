package pt.snowplow.framework;

import android.util.Log;

public class Logger {

	private static final String TAG = "DodgingXmas";

	private Logger() {

	}

	public static void log( String message ) {
		log( "", message );
	}

	public static void log( String tag, String message ) {
		log( tag, message, null );
	}

	public static void log( String message, Throwable t ) {
		log( "", message, t );
	}

	public static void log( String tag, String message, Throwable t ) {
		Log.d( TAG, tag + " :: " + message, t );
	}
}
