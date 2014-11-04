package miv.android;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GCMHttpConnect extends Thread{
	private static final String TAG = "HTTP";
	private Request mRequest;
	private String mString;
	
	public GCMHttpConnect(String url, Request request) {
		mString = url;				
		mRequest = request;
	}
	
	@Override
	public void run() {
		download(mString);
		
		Message	msg = new Message();
		msg.what = 0;
		mHandler.sendMessage(msg);
	}
	
	Handler	mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (mRequest != null) {		
				mRequest.OnComplete();
			}
		}		
	};
	
	public static interface Request {
		public void OnComplete();
	}
	
	public void download(String address) {
		StringBuilder jsonHtml = new StringBuilder();
    	try{
    		//?�결 url ?�정
    		URL url = new URL(address);
    		//컨넥??객체 ?�성
    		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
    		conn.setDefaultUseCaches(false);                                           
            conn.setDoInput(true);                         // ?�버?�서 ?�기 모드 �?��
            conn.setDoOutput(false);                       // ?�버�??�기 모드 �?�� 
            conn.setRequestMethod("POST");         // ?�송 방식??POST
            
    		//?�결?�었??
    		if(conn != null){
    			conn.setConnectTimeout(10000);
    			conn.setUseCaches(false);
    			//?�결?�인 코드�?리턴?�었????
    			if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
    				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
    				for(;;){
    					String line = br.readLine();
    					if(line == null) break;
    					jsonHtml.append(line);
    				}
    				br.close();
    			}
    			conn.disconnect();
    		}
    	}catch(Exception e){
    		Log.w(TAG, e.getMessage());
    	}
	}
}