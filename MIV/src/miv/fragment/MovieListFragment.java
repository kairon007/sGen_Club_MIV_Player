package miv.fragment;

import java.util.ArrayList;

import miv.adapter.VideoGalleryAdapter;
import miv.android.R;
import miv.android.VideoPlayerActivity;
import miv.item.VideoItemInfo;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MovieListFragment extends Fragment{
	
	ArrayList<VideoItemInfo> videoInfo = new ArrayList<VideoItemInfo>();;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("omg", "startstart");
		
		View v = inflater.inflate(R.layout.fragment_movie_list, container, false);


		videoInfo = this.showAmountVideoList();

		ListView movieListView = (ListView) v.findViewById(R.id.List_Movie);
		movieListView.setAdapter(new VideoGalleryAdapter(getActivity(), videoInfo));
		movieListView.setOnItemClickListener(new OnItemClickListener(){
			
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				if (videoInfo.get(position) != null) {

					String videoFilePath = videoInfo.get(position).filePath;

					Intent intentVideoPlayer = new Intent(getActivity(), VideoPlayerActivity.class);
					intentVideoPlayer.putExtra("videoFilePath", videoFilePath);
					intentVideoPlayer.putExtra("title", videoInfo.get(position).title);
					startActivity(intentVideoPlayer);
				}
			}
		});
		return v;
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<VideoItemInfo> showAmountVideoList(){
		
		Cursor mediaCursor=null, thumbCursor=null;
		
		String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
				MediaStore.Video.Thumbnails.VIDEO_ID };

		String[] mediaColumns = { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.DURATION,
				MediaStore.Video.Media.SIZE };
		
		mediaCursor = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns, null, null, null);
		
		ArrayList<VideoItemInfo> videoInfo = new ArrayList<VideoItemInfo>();
		
		if (mediaCursor.moveToFirst()) {
			do {
				VideoItemInfo videoItem = new VideoItemInfo();
				int id = mediaCursor.getInt(mediaCursor.getColumnIndex(MediaStore.Video.Media._ID));
				thumbCursor = getActivity().managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID + "=" + id, null, null);
				if (thumbCursor.moveToFirst()) {
					videoItem.thumbPath = thumbCursor.getString(thumbCursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
				}

				videoItem.filePath 	= mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
				videoItem.title		= mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
				videoItem.mimeType 	= mediaCursor.getString(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
				videoItem.duration 	= mediaCursor.getInt(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
				videoItem.size 		= mediaCursor.getInt(mediaCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
				
				videoInfo.add(videoItem);
				
			} while (mediaCursor.moveToNext());
		}
	
		Log.i("omg", videoInfo.get(0).filePath);
		return videoInfo;
	}
}
