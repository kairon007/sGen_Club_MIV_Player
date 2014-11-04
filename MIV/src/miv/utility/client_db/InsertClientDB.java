package miv.utility.client_db;

import android.app.Activity;
import android.os.Bundle;

public class InsertClientDB extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Receive from server data
		this.saveMatchedAudioInfo("CF", "00:00:00 ~ 00:00:15", "what a wonderful world", "http://www.youtube.com/watch?v=F-3ox-6WhBA", "Joey Ramone", "Rocket to Russia");
		
	}

	/* When received audio information from server. */
	public void saveMatchedAudioInfo(String videoName, String matchedDuration, String audioName, String youtubeURL, String artist, String album){
		DBHandler dbHandler = DBHandler.open(this);
		dbHandler.insert(videoName, matchedDuration, audioName, youtubeURL, artist, album);
		
	} 
}
