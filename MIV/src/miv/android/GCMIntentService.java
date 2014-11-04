package miv.android;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import miv.utility.client_db.DBHandler;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{
	private static final String SENDER_ID = "681965916351";
	private GCMHttpConnect httpConnect = null;
	private GCMHttpConnect.Request httpRequest = new GCMHttpConnect.Request() {
		
		
		public void OnComplete() {
			// TODO Auto-generated method stub
			showToast();
		}
	};

	
	public GCMIntentService() {
		super(SENDER_ID);
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		if (arg1.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			Log.e("gcmtest","onMessage Call");
			showMessage(arg0, arg1);
		}
	}

	@Override
	protected void onRegistered(Context arg0, String regID) {
		// TODO Auto-generated method stub
		if(!regID.equals("") || regID != null)
			Log.e("gcmtest", "onRegistered!! " + regID);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void showToast(){
		Toast.makeText(this, "RegID ?�록 ?�료", Toast.LENGTH_LONG).show();
	}

	private void showMessage(Context context, Intent intent){
		String title = intent.getStringExtra("title");
		String msg = intent.getStringExtra("msg");
		String ticker = intent.getStringExtra("ticker");
		
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Activity.NOTIFICATION_SERVICE);
		
//		?�당 ?�플???�행?�는 ?�벤?��? ?�고?�을 ???�래 주석????��주세??
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, 
//				new Intent(context, ?�플??처음 ?�작?�는 ?�래?�명.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 0);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);

//		
//		String videoname=null, timerange=null, musictitle=null, artist=null;
//		String albumtitle=null, youtubeurl=null, subtitles=null;
//		
//		videoname = msg.split("|")[0];
//		System.out.println(videoname);
//		timerange = msg.split("|")[1];
//		musictitle = msg.split("|")[2];
//		artist = msg.split("|")[3];
//		albumtitle = msg.split("|")[4];
//		youtubeurl = msg.split("|")[5];
//		subtitles = msg.split("|")[6];

		
		String[] array;
		
		array = msg.split("\n");
		
		
		String path = "/storage/extSdCard/MIV_Audio/";
		String fileName = array[0] + ".smi";
		
		String file = path + fileName;
		
		FileOutputStream fos = null;
		
		this.saveMatchedAudioInfo(array[0], array[1], array[2], array[5], array[3], array[4]);
		
		try{
			
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			
			int i=6;
			
			//fos = new FileOutputStream(file);
			while(i < array.length){
				//fos.write((array[i]+"\n").getBytes());
				outputStreamWriter.write(array[i]+" \n");
				i++;
			}
			
			outputStreamWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
        Log.e("aaa","msg : "+ array[0]);
        
        Log.e("abc","111");
		//notificationManager.notify(0, notification);

        long[] vibrate = {0,100,200,300};
        NotificationManager mNotificaionManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle("MIV")
			.setContentText(array[0] + ", " + array[2] + "-" + array[3])
			.setVibrate(vibrate);
		
		
		mNotificaionManager.notify(1, mBuilder.build());
        
	}
	
	public void saveMatchedAudioInfo(String videoName, String matchedDuration, String audioName, String youtubeURL, String artist, String album){
		DBHandler dbHandler = DBHandler.open(this);
		dbHandler.insert(videoName, matchedDuration, audioName, youtubeURL, artist, album);
		
	} 
}
