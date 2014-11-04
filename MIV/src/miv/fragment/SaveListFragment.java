package miv.fragment;

import java.util.ArrayList;

import miv.adapter.SaveGalleryAdapter;
import miv.android.R;
import miv.item.SaveItemInfo;
import miv.utility.client_db.DBHandler;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SaveListFragment extends Fragment {

	ArrayList<SaveItemInfo> saveInfo = new ArrayList<SaveItemInfo>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("fragment", "SaveListFragment is started");
		View v = inflater.inflate(R.layout.fragment_save_list, container, false);

		
		
		
		saveInfo = this.showAmountSaveList();
		
		ListView saveListView = (ListView) v.findViewById(R.id.List_Save);
		saveListView.setAdapter(new SaveGalleryAdapter(getActivity(), saveInfo));
		saveListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> l, View v, int position, long id) {
				if (saveInfo.get(position) != null) {

					Intent intentYoutubeURL = new Intent(Intent.ACTION_VIEW);
					Uri youtubeURL = Uri.parse(saveInfo.get(position).youtubeURL);

					intentYoutubeURL.setData(youtubeURL);
					startActivity(intentYoutubeURL);

				}
			}
		});

		return v;
	}
	
	
	/* Show SAVE LIST */
	@SuppressWarnings("deprecation")
	public ArrayList<SaveItemInfo> showAmountSaveList() {

		DBHandler dbHandler = null;
		Cursor clientCursor = null, mediaCursor = null, thumbCursor = null;

		ArrayList<SaveItemInfo> saveInfo = new ArrayList<SaveItemInfo>();
		SaveItemInfo element = new SaveItemInfo();

		String[] thumbColumns = { MediaStore.Video.Thumbnails.DATA,
				MediaStore.Video.Thumbnails.VIDEO_ID };

		String[] mediaColumns = { MediaStore.Video.Media._ID,
				MediaStore.Video.Media.DATA, MediaStore.Video.Media.TITLE,
				MediaStore.Video.Media.MIME_TYPE,
				MediaStore.Video.Media.DURATION, MediaStore.Video.Media.SIZE };

		boolean existFlag = false;

		dbHandler = DBHandler.open(getActivity());
		clientCursor = dbHandler.selectAll();
		while (clientCursor.moveToNext()) {
			element.videoTitle = clientCursor.getString(1);
			element.matchedDuration = clientCursor.getString(2);
			element.audioTitle = clientCursor.getString(3);
			element.youtubeURL = clientCursor.getString(4);
			element.artist = clientCursor.getString(5);
			element.album = clientCursor.getString(6);

			mediaCursor = getActivity().managedQuery(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
					null, null, null);
			while (mediaCursor.moveToNext()) {
				if (mediaCursor
						.getString(
								mediaCursor
										.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE))
						.equals(clientCursor.getString(1))) {
					int id = mediaCursor.getInt(mediaCursor
							.getColumnIndex(MediaStore.Video.Media._ID));
					thumbCursor = getActivity().managedQuery(
							MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
							thumbColumns, MediaStore.Video.Thumbnails.VIDEO_ID
									+ "=" + id, null, null);
					if (thumbCursor.moveToFirst()) {
						element.thumbPath = thumbCursor
								.getString(thumbCursor
										.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
					}
					element.mimeType = mediaCursor
							.getString(mediaCursor
									.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
					break;
				}
			}
			for (int i = 0; i < saveInfo.size(); i++) {
				if (saveInfo.get(i).audioTitle.equals(element.audioTitle)) {
					existFlag = true;
				}
			}
			if (existFlag == true) {
				existFlag = false;
			} else {
				saveInfo.add(element);
			}
		}
		clientCursor.close();
		return saveInfo;
	}
}
