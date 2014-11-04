package miv.android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import miv.android.R;
import miv.utility.ffmpeg.FfmpegJob;
import miv.utility.ffmpeg.Utils;
import miv.view.videocontroller.VideoControllerView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class VideoPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl{
	
	public String videoPath;
	String title;
	
	private static final String TAG_1 = "extractAudio";
    private String mFfmpegInstallPath;
	
    private TextView subtitle;
   	private ArrayList<smiData> parsedSmi;
   	private boolean useSmi;
   	private int countSmi;
   	
   	//Vibrator Mode Flag
   	private boolean vibrateFlag = false;
   	private boolean subtitleFlag = false;
    
	SurfaceView videoSurface;
	MediaPlayer player;
	VideoControllerView controller;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_video_player);
	    
	    SharedPreferences prefs = getSharedPreferences("config", 0);
	    vibrateFlag = prefs.getBoolean("vibrate", false);
	    subtitleFlag = prefs.getBoolean("subtitle", false);
	    if(prefs.getBoolean("ear", false)==false){
	    	vibrateFlag = false;
		    subtitleFlag = false;
	    }else{
	    	vibrateFlag = true;
		    subtitleFlag = true;
	    }
	    
	    
	    videoSurface = (SurfaceView)findViewById(R.id.videoSurface);
	    SurfaceHolder videoHolder = videoSurface.getHolder();
	    videoHolder.addCallback((this));
	    
	    Intent intentVideoPlayer = getIntent();
	    videoPath = intentVideoPlayer.getStringExtra("videoFilePath");
	    title = intentVideoPlayer.getStringExtra("title");
	    
	    player = new MediaPlayer();
	    controller = new VideoControllerView(this, title, this);
	    
	    try {
	    	smiRead(videoPath);
	    	
	    	player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource(videoPath);
			player.setOnPreparedListener(this);
			
			if(subtitleFlag == true && useSmi == true) {
				
	        	new Thread(new Runnable() 
	            {
	            	public void run() 
	            	{
	            		try {
	            			while(true) {
	            				Thread.sleep(300);
	            				myHandler.sendMessage(myHandler.obtainMessage());
	            			}
	            		} catch (Throwable t) {
	            			// Exit Thread
	            		}
	            	}
	            }).start();
	        }
			
			
			if(vibrateFlag == true && videoPath.equals("/storage/extSdCard/Movie/Star Wars Episode VII 2015.mp4")){
				
				new Thread(new Runnable() 
	            {
	            	public void run() 
	            	{
	            		try {
	            			while(true) {
	            				
	            				boolean flag = true;
	            				
	            				Thread.sleep(300);
	            				Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	            				if(flag && player.getCurrentPosition() >= 37500 && player.getCurrentPosition() < 39000){
	            					vibe.vibrate(1900);
	            					flag = false;
	            				}
	            				flag = true;
	            				
	            				
	            				if(flag && player.getCurrentPosition() >= 41500 && player.getCurrentPosition() < 43000){
	            					vibe.vibrate(1900);
	            					flag = false;
	            				}
	            				flag = true;
	            				
	            				if(player.getCurrentPosition() >= 44000 && player.getCurrentPosition() < 46000){
	            					vibe.vibrate(1900);
	            					flag = false;
	            				}
	            				flag = true;
	            				
	            				
	            			}
	            		} catch (Throwable t) {
	            			// Exit Thread
	            		}
	            	}
	            }).start();
			}
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		controller.show();
		return false;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		player.setDisplay(holder);
		player.prepareAsync();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		controller.setMediaPlayer(this);
		controller.setAnchorView((FrameLayout)findViewById(R.id.videoSurfaceContainer));
		player.start();
		
	}
	
	@Override
	public boolean canPause() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		player.start();
	}
	
	Handler myHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		countSmi = getSyncIndex(player.getCurrentPosition());
    		subtitle.setText(Html.fromHtml(parsedSmi.get(countSmi).gettext()));
    	}
    };
    public int getSyncIndex(long playTime) {
    	int l=0,m,h=parsedSmi.size();
    	
//    	while(l <= h) {
//    		
//    		m = (1+h)/2;
//
//    		if(parsedSmi.get(m).gettime() <= playTime && playTime < parsedSmi.get(m+1).gettime()) {
//    			return m;
//    		}
//    		if(playTime > parsedSmi.get(m+1).gettime()) {
//    			l=m+1;
//    		} else {
//    			h=m-1;
//    		}
//    	}
    	
    	while(l <= h){
    		
    		if(l == h-1 && parsedSmi.get(l).gettime() <= playTime)
    			return l;
    		
    		else if(l == h && parsedSmi.get(l).gettime() <= playTime)
    			return l;
    		
    		else if(parsedSmi.get(l).gettime() <= playTime && parsedSmi.get(l+1).gettime() > playTime){
    			
    			return l;
    			
    		}
    		
    		l++;
    		
    	}
    	return 0;
    }

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		player.pause();
	}

	@Override
	public int getDuration() {
		// TODO Auto-generated method stub
		return player.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return player.getCurrentPosition();
	}

	@Override
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		player.seekTo(pos);
	}

	@Override
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return player.isPlaying();
	}

	@Override
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isFullScreen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void toggleFullScreen() {
		// TODO Auto-generated method stub
		
	}

	public String confirmPointVibrate(int position) {
		// TODO Auto-generated method stub
		Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		vibe.vibrate(100);
		return videoPath;
	}

	public void extractAudio(String videoPath, int startPoint, int finishPoint) {
		// TODO Auto-generated method stub
		
		startPoint /= 1000;
		finishPoint /= 1000;
		
		/* Audio file information : just same audio file name - source.m4a */
		String dirPath = "/storage/extSdCard/MIV_Audio/";
		String audioPath = dirPath + "source.m4a";
		
		File directory = new File(dirPath);
		File audioFile = new File(audioPath);
		if(!directory.exists()){
			directory.mkdirs();
		} else if(audioFile.exists()){
			audioFile.delete();
		}
		
		/* Install FFmpeg execute file if file is not installed. */
		File ffmpegFile = new File(getCacheDir(), "ffmpeg");
        mFfmpegInstallPath = ffmpegFile.toString();
        
        if (!ffmpegFile.exists()) {
                try {
                        ffmpegFile.createNewFile();
                } catch (IOException e) {
                        Log.e(TAG_1, "Failed to create new file!", e);
                }
                Utils.installBinaryFromRaw(this, R.raw.ffmpeg, ffmpegFile);
        }
        ffmpegFile.setExecutable(true);
		
		/* Set FfmpegJob class value */
		FfmpegJob job = new FfmpegJob(mFfmpegInstallPath);
		job.inputPath = videoPath;
        job.outputPath = audioPath;
        job.startPoint = startPoint;
        job.processTime = finishPoint - startPoint;
		
        /* Execute command line */
        job.create().run();
        
        /* Test Message - will be deleted */
		Toast.makeText(this, "Audio Extractor Complete.", Toast.LENGTH_SHORT).show();
	}
	
	class smiData{
		long time;
		String text;
		smiData(long time, String text){
			this.time = time;
			this.text = text;
		}
		
		public long gettime()	{
			return time;
		}
		
		public String gettext() {
			return text;
		}
	}
	
	public void smiRead(String videoPath){
		
		String smiPath = videoPath.substring(0,videoPath.lastIndexOf(".")) + ".smi";
        File smiFile = new File(smiPath);
        if(smiFile.isFile() && smiFile.canRead()) {
        	
        	useSmi = true;
        	parsedSmi = new ArrayList<smiData>();
        	try {
        		BufferedReader in = new BufferedReader(new InputStreamReader(
        				new FileInputStream(new File(smiFile.toString())),"UTF-8"));

        		String s;
        	    long time = -1;
        	    String text = null;
        	    boolean smistart = false;
        	    
        	    while ((s = in.readLine()) != null) {
        	    	if(s.contains("<SYNC")) {
        	    		smistart = true;
        	    		if(time != -1) {
        	    			parsedSmi.add(new smiData(time, text));
        	    		}
        	    		time = Integer.parseInt(s.substring(s.indexOf("=")+1, s.indexOf(">")));
        	    		text = s.substring(s.indexOf(">")+1, s.length());
        	    		text = text.substring(text.indexOf(">")+1, text.length());
        	    	} else {
        	    		if(smistart == true) {
        	    			text += s;
        	    		}
        	    	}
        	    }
        	    in.close();
        	} catch (IOException e) {
        	    System.err.println(e);
        	    System.exit(1);
        	}
        } else {
        	useSmi = false;
        }
        
        subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText("");
        
    }
}
