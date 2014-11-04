package miv.utility.client_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBHandler {
	
	@SuppressWarnings("unused")
	private Context ctx;  
    private DBHelper helper;  
    private SQLiteDatabase db;  
      
    public DBHandler(Context ctx) {  
        this.ctx = ctx;  
        helper = new DBHelper(ctx);  
        db = helper.getWritableDatabase(); //DB∞° open µ   
    }  
      
    public static DBHandler open(Context ctx) throws SQLException{  
        DBHandler handler = new DBHandler(ctx);  
        return handler;  
    }  
      
    public void close(){  
        helper.close();  
    }  
      
    //SQLπÆ ¿€º∫  
    //INSERT  
    public long insert(String VIDEO_NAME, String DURATION, String AUDIO_NAME, String YOUTUBE_URL, String ARTIST, String ALBUM){  
        ContentValues values = new ContentValues();  
        
        values.put("VIDEO_NAME", VIDEO_NAME);  
        values.put("DURATION", DURATION);  
        values.put("AUDIO_NAME", AUDIO_NAME);  
        values.put("YOUTUBE_URL", YOUTUBE_URL);  
        values.put("ARTIST", ARTIST);  
        values.put("ALBUM", ALBUM);  
  
        long result = db.insert("SAVE_SECTION", null, values);  
        return result;  
    }  
      
    //SELECT  
    public Cursor selectAll(){  
        Cursor cursor = db.query(true, "SAVE_SECTION",   
            new String[]{"SAVE_ID", "VIDEO_NAME", "DURATION", "AUDIO_NAME", "YOUTUBE_URL", "ARTIST", "ALBUM"},   
            null, null, null, null, null, null);  
        return cursor;  
    }  
}  
