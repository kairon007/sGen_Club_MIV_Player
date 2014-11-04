//package miv.android;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//public class FolderListActivity extends Activity {
//
//	private ArrayList<FolderItemInfo> folderInfo = new ArrayList<FolderItemInfo>();
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_folder_list);
//	
//		folderInfo = this.showAmountFolderList();
//		ListView folderListView = (ListView) this.findViewById(R.id.List_Folder);
//		folderListView.setAdapter(new FolderGalleryAdapter(this, folderInfo));
//	}
//	
//	
//	@SuppressWarnings("deprecation")
//	public ArrayList<FolderItemInfo> showAmountFolderList(){
//		
//		Cursor mediaCursor=null;
//		
//		String[] mediaColumns = { MediaStore.Video.Media._ID,
//				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
//				MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.DURATION,
//				MediaStore.Video.Media.SIZE };
//		
//		mediaCursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
//		
//		ArrayList<FolderItemInfo> folderPathList = new ArrayList<FolderItemInfo>();
//		
//		if (mediaCursor.moveToFirst()) {
//			do {
//				String videoPath = mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
//				File videoFile = new File(videoPath);
//				String folderPath = videoFile.getParent();
//				String folderName = videoFile.getParentFile().getName();
//				
//				boolean existFlag = false;
//				for(int i=0; i<folderPathList.size(); i++){
//					if(folderPathList.get(i).folderPath.equals(folderPath)){
//						existFlag = true;
//					}
//				}
//				if(existFlag == true){
//					existFlag = false;
//				} else {
//					FolderItemInfo element = new FolderItemInfo();
//					element.folderPath = folderPath;
//					element.folderTitle = folderName;
//					folderPathList.add(element);
//				}
//				
//			} while (mediaCursor.moveToNext());
//		}
//	
//		return folderPathList;
//	}
//	
//	@SuppressWarnings("deprecation")
//	public ArrayList<VideoItemInfo> showChildVideoList(String folderPath){
//		
//		Cursor mediaCursor=null, thumbCursor=null;
//		
//		String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
//				MediaStore.Video.Thumbnails.VIDEO_ID };
//
//		String[] mediaColumns = { MediaStore.Video.Media._ID,
//				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
//				MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.DURATION,
//				MediaStore.Video.Media.SIZE };
//		
//		mediaCursor = managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
//		
//		ArrayList<VideoItemInfo> childVideoList = new ArrayList<VideoItemInfo>();
//		
//		if (mediaCursor.moveToFirst()) {
//			do {
//				VideoItemInfo videoItem = new VideoItemInfo();
//				int id = mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Video.Media._ID));
//				thumbCursor = managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null, null);
//				if (thumbCursor.moveToFirst()) {
//					videoItem.thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
//				}
//
//				videoItem.filePath 	= mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
//				videoItem.title		= mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
//				videoItem.mimeType 	= mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
//				videoItem.duration 	= mediaCursor.getInt(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
//				videoItem.size 		= mediaCursor.getInt(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
//				
//				File parent = new File(videoItem.filePath);
//				if(folderPath.equals(parent.getParent())){
//					childVideoList.add(videoItem);
//				}
//				
//			} while (mediaCursor.moveToNext());
//		}
//		
//		return childVideoList;
//	}
//	
//	class FolderItemInfo {
//		String folderPath;
//		String folderTitle;
//	}
//	
//	class VideoItemInfo {
//		String filePath;
//		String mimeType;
//		String thumbPath;
//		String title;
//		int duration;
//		int size;
//	}
//	
//	class FolderGalleryAdapter extends BaseAdapter {
//		private Context context;
//		private List<FolderItemInfo> folderItems;
//
//		LayoutInflater inflater;
//
//		public FolderGalleryAdapter(Context _context,
//				ArrayList<FolderItemInfo> _items) {
//			context = _context;
//			folderItems = _items;
//
//			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		}
//
//		public int getCount() {
//			return folderItems.size();
//		}
//
//		public Object getItem(int position) {
//			return folderItems.get(position);
//		}
//
//		public long getItemId(int position) {
//			return position;
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			View folderRow = inflater.inflate(R.layout.folder_item, null);
//
//			TextView folderTitle = (TextView) folderRow.findViewById(R.id.Item_Folder_Title);
//			folderTitle.setText(folderItems.get(position).folderTitle);
//
//			return folderRow;
//		}
//	}
//}
