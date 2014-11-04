package miv.utility.client_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{  
    private static final String DB_NAME = "SAVE_SECTION";  // ����� DB �̸�  
    private static final int    DB_VER = 1;             // DB ����  
      
    public DBHelper(Context context) {  
        super(context, DB_NAME, null, DB_VER);  
    }  
      
    @Override  
    public void onCreate(SQLiteDatabase db) {  
        //���̺� ���� �� �ʱ� �۾�   
        String sql = "create table SAVE_SECTION(" +  
            "SAVE_ID integer primary key autoincrement," +  
            "VIDEO_NAME text not null, DURATION text not null," +
            "AUDIO_NAME text not null, YOUTUBE_URL text not null," +
            "ARTIST text not null, ALBUM text not null)";  
        db.execSQL(sql);    
    }  
      
    @Override  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
        //DB�� ������ �ٲ�� ����  
        db.execSQL("drop table if exist SAVE_SECTION");  
        onCreate(db);  
    }  
}  