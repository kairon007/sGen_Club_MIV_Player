package miv.android;

import miv.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class OpeningActivity extends Activity {
	waitThread wait;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.activity_opening);
	    
	    wait = new waitThread(this);
	    wait.start();
	    
	    // TODO Auto-generated method stub
	}
	
	public class waitThread extends Thread{
		Activity activity;
		
		public waitThread(Activity activity) {
			super();
			this.activity = activity;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(activity, MovieListActivity.class);
		    startActivityForResult(intent, 1);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		finish();
	}
	
}
