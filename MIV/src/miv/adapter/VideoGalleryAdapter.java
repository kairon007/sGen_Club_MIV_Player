package miv.adapter;

import java.util.ArrayList;
import java.util.List;

import miv.android.R;
import miv.item.VideoItemInfo;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoGalleryAdapter extends BaseAdapter {
	private Context context;
	private List<VideoItemInfo> videoItems;

	LayoutInflater inflater;

	public VideoGalleryAdapter(Context _context,
			ArrayList<VideoItemInfo> _items) {
		context = _context;
		videoItems = _items;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return videoItems.size();
	}

	public Object getItem(int position) {
		return videoItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View videoRow = inflater.inflate(R.layout.movie_item, null);
		
		ImageView videoThumb = (ImageView) videoRow.findViewById(R.id.imgThumb);
		if (videoItems.get(position).thumbPath != null) {
			videoThumb.setImageURI(Uri.parse(videoItems.get(position).thumbPath));
		}
		
		Log.i("mimeType", "position: "+position+" mimeType : "+videoItems.get(position).mimeType);
		
		String videoType = videoItems.get(position).mimeType.substring(6);
		
		ImageView imgVideoType = (ImageView) videoRow.findViewById(R.id.imgVideoType);
		
		if(videoType.equals("avi")){
			imgVideoType.setImageResource(R.drawable.imgavi);
		}else if(videoType.equals("wmv") || videoType.equals("x-ms-wmv")){
			imgVideoType.setImageResource(R.drawable.imgwmv);
		}else if(videoType.equals("asf")){
			imgVideoType.setImageResource(R.drawable.imgasf);
		}else if(videoType.equals("mpg")){
			imgVideoType.setImageResource(R.drawable.imgmpg);
		}else if(videoType.equals("mkv")){
			imgVideoType.setImageResource(R.drawable.imgmkv);
		}else if(videoType.equals("mov")){
			imgVideoType.setImageResource(R.drawable.imgmov);
		}else if(videoType.equals("3gpp")){
			imgVideoType.setImageResource(R.drawable.img3gpp);
		}else if(videoType.equals("mp4")){
			imgVideoType.setImageResource(R.drawable.imgmp4);
		}else{
			imgVideoType.setImageResource(R.drawable.imgavi);
		}

		TextView videoTitle = (TextView) videoRow.findViewById(R.id.txtTitle);
		videoTitle.setText(videoItems.get(position).title);
		
		TextView txtDuration = (TextView) videoRow.findViewById(R.id.txtDuration);
		txtDuration.setText(""+((videoItems.get(position).duration/1000)/3600<10 ? "0"+(videoItems.get(position).duration/1000)/3600 : (videoItems.get(position).duration/1000)/3600)+":"+((videoItems.get(position).duration/1000)%3600/60<10 ? "0"+(videoItems.get(position).duration/1000)%3600/60 : (videoItems.get(position).duration/1000)%3600/60)+":"+((videoItems.get(position).duration/1000)%60<10 ? "0"+(videoItems.get(position).duration/1000)%60 : (videoItems.get(position).duration/1000)%60));

		return videoRow;
	}
}
