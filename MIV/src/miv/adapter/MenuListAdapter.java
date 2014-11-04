package miv.adapter;

import java.util.ArrayList;

import miv.android.R;
import miv.item.MenuItemInfo;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter{
	//public ViewHolder holder = new ViewHolder();
	public interface MenuListener {
        void onActiveViewChanged(View v);
    }
	
	Context context;
	ArrayList<MenuItemInfo> arrMenu;
	int mActivePosition = -1;
	MenuListener mListener;


	public MenuListAdapter(Context context, ArrayList<MenuItemInfo> arrMenu){
		this.context = context;
		this.arrMenu = arrMenu;
	}
	
	public void setListener(MenuListener listener) {
        mListener = listener;
    }
	
	public void setActivePosition(int activePosition){
		mActivePosition = activePosition;
	}
	@Override
    public int getItemViewType(int position) {
        return getItem(position) instanceof MenuItemInfo ? 0 : 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItem(position) instanceof MenuItemInfo;
    }
    
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrMenu.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrMenu.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("getView", "getView");
		
		View v = convertView;
		
		if(v==null){
			v = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
			v.findViewById(R.id.imgView).setBackgroundResource((arrMenu.get(position)).getImg());
			if(position==0){
				v.setBackgroundResource(R.drawable.selected);
			}else if(position==4){
				v.setClickable(false);
			}else if(position==5){
				v.setClickable(false);
			}else if(position==5){
				v.setClickable(false);
			}
			TextView txtTitle = (TextView) v.findViewById(R.id.textTitle);
			txtTitle.setText(arrMenu.get(position).getTitle());
		}
		//v.setTag(R.id.both, position);
		
		if (position == mActivePosition) {
            mListener.onActiveViewChanged(v);
        }
		
		return v;
	}
}
