package miv.android;

import miv.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gcm.GCMRegistrar;

public class ConnectServer extends Activity {
	private static final String TAG = "gcmtest";
	private static final String SENDER_ID = "681965916351";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        //GCM ?�록?��?
      	final String regId = GCMRegistrar.getRegistrationId(this);
      	//?�록??ID�??�으�?ID값을 ?�어?�니??
      	if (regId.equals("") || regId == null) {
      		Log.e(TAG,"regId Is null");
      		GCMRegistrar.register(this, SENDER_ID);
      	}else{
      		Log.e(TAG, "Already Registered : " + regId);
      	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
