package miv.fragment;

import java.util.ArrayList;

import miv.android.R;
import miv.item.VideoItemInfo;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

public class ConfigureFragment extends Fragment{
	
	ArrayList<VideoItemInfo> videoInfo = new ArrayList<VideoItemInfo>();;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_configure, container, false);

		SharedPreferences prefs = getActivity().getSharedPreferences("config", 0);
		final SharedPreferences.Editor editor = prefs.edit();
		
		final Switch switchVibrate = (Switch) v.findViewById(R.id.switchVibrate);
		switchVibrate.setChecked(prefs.getBoolean("vibrate", false));
		switchVibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					editor.putBoolean("vibrate", true);
				}else{
					editor.putBoolean("vibrate", false);
				}
				editor.commit();
			}
		});
		final Switch switchSubtitle = (Switch) v.findViewById(R.id.switchSubtitle);
		switchSubtitle.setChecked(prefs.getBoolean("subtitle", false));
		switchSubtitle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					editor.putBoolean("subtitle", true);
				}else{
					editor.putBoolean("subtitle", false);
				}
				editor.commit();
			}
		});
		Switch switchEar = (Switch) v.findViewById(R.id.switchEar);
		switchEar.setChecked(prefs.getBoolean("ear", false));
		switchEar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked){
					editor.putBoolean("ear", true);
					editor.putBoolean("vibrate", true);
					switchVibrate.setChecked(true);
					editor.putBoolean("subtitle", true);
					switchSubtitle.setChecked(true);
				}else{
					editor.putBoolean("ear", false);
					editor.putBoolean("vibrate", false);
					switchVibrate.setChecked(false);
					editor.putBoolean("subtitle", false);
					switchSubtitle.setChecked(false);
				}
				editor.commit();
			}
		});
		
		return v;
	}
}
