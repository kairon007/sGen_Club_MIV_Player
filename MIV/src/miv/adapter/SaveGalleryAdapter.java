package miv.adapter;

import java.util.ArrayList;
import java.util.List;

import miv.android.R;
import miv.item.SaveItemInfo;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SaveGalleryAdapter extends BaseAdapter {
	private Context context;
	private List<SaveItemInfo> saveItems;

	LayoutInflater inflater;

	public SaveGalleryAdapter(Context _context,
			ArrayList<SaveItemInfo> _items) {
		context = _context;
		saveItems = _items;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return saveItems.size();
	}

	public Object getItem(int position) {
		return saveItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View videoRow = inflater.inflate(R.layout.save_item, null);
		ImageView videoThumb = (ImageView) videoRow
				.findViewById(R.id.imgThumb);
		if (saveItems.get(position).thumbPath != null) {
			videoThumb
					.setImageURI(Uri.parse(saveItems.get(position).thumbPath));
		}

		TextView txtDuration = (TextView) videoRow.findViewById(R.id.txtDuration);
		txtDuration.setText(saveItems.get(position).matchedDuration);
	
		
		TextView videoTitle = (TextView) videoRow
				.findViewById(R.id.txtTitle);
		videoTitle.setText(saveItems.get(position).audioTitle);
		
		TextView txtMusic = (TextView) videoRow.findViewById(R.id.txtMusic);
		txtMusic.setText(saveItems.get(position).artist+"/"+saveItems.get(position).album);

		return videoRow;
	}
}
