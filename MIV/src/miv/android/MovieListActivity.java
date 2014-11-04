package miv.android;

import java.util.ArrayList;

import miv.adapter.MenuListAdapter;
import miv.android.R;
import miv.fragment.ConfigureFragment;
import miv.fragment.MovieListFragment;
import miv.fragment.SaveListFragment;
import miv.item.MenuItemInfo;
import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

public class MovieListActivity extends Activity implements MenuListAdapter.MenuListener{

	FragmentManager mFragmentManager;
	FragmentTransaction mFragmentTransaction;
	MenuDrawer mMenuDrawer;
	Context mContext = this;
	String mCurrentFragmentTag;
	ArrayList<MenuItemInfo> menuItems;
	MenuListAdapter menuAdapter;
	int ActivePosition;
	ListView menuList;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle inState) {
		super.onCreate(inState);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		mFragmentManager = getFragmentManager();
		
		menuItems = new ArrayList<MenuItemInfo>();
	    menuItems.add(new MenuItemInfo("전체 목록", "movieListFragment", R.drawable.slideicon_01));
	    menuItems.add(new MenuItemInfo("폴더 목록", "folderListFragment", R.drawable.slideicon_02));
	    menuItems.add(new MenuItemInfo("저장 구간", "saveListFragment", R.drawable.slideicon_03));
	    menuItems.add(new MenuItemInfo("설정", "configureFragment", R.drawable.slideicon_04));
	    menuItems.add(new MenuItemInfo("", "", 0));
	    menuItems.add(new MenuItemInfo("기능 추가", "functionFragment", R.drawable.slideicon_05));
	    menuItems.add(new MenuItemInfo("도움말", "helpFragment", R.drawable.slideicon_06));
	    
	    menuList = new ListView(this);
	    menuList.setDividerHeight(0);
	    menuList.setPadding(0, 0, 0, 0);
	    menuList.setBackgroundColor(0xff262626);
	    
	    menuAdapter = new MenuListAdapter(this, menuItems);
	    menuAdapter.setListener(this);
        menuAdapter.setActivePosition(ActivePosition);
        menuList.setAdapter(menuAdapter);
        
        menuList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	            ActivePosition = position;
	            mMenuDrawer.setActiveView(view, position);
	            menuAdapter.setActivePosition(position);
	            onMenuItemClicked(position, (MenuItemInfo) menuAdapter.getItem(position));
	        }
		});
        
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, getDrawerPosition(), getDragMode());
        menuList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mMenuDrawer.setMenuView(menuList);
        mMenuDrawer.setMenuSize(900);
        mMenuDrawer.setBackgroundColor(0xff262626);
        mMenuDrawer.setContentView(R.layout.activity_fragment);
        mMenuDrawer.setOnDrawerStateChangeListener(new MenuDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
            	Log.e("omg","onDrawerStateChange");
                if (newState == MenuDrawer.STATE_CLOSED) {
                    commitTransactions();
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                // Do nothing
            }
        });
        if (inState != null) {
            mCurrentFragmentTag = inState.getString("current");
        } else {
            mCurrentFragmentTag = ((MenuItemInfo)menuAdapter.getItem(0)).getName();
            attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(mCurrentFragmentTag),
                    mCurrentFragmentTag);
            Log.e("Fragment", mMenuDrawer.getContentContainer().toString());
            commitTransactions();
        }
	    setContentView(R.layout.activity_fragment);
	    
	    ActionBar actionbar = getActionBar();
	    actionbar.setDisplayShowTitleEnabled(true);
	    actionbar.setHomeButtonEnabled(true);
	    actionbar.setTitle("전체 목록");
	    actionbar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbarbackground));
//	    View v = getLayoutInflater().inflate(R.layout.actionbar, null);
//	    TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
//	    txtTitle.setText("MIV");
//	    actionbar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
//	    actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	    
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return true;
	}
	
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
	        case android.R.id.home:
	            mMenuDrawer.toggleMenu();
	            return true;
	    }
		
		return super.onOptionsItemSelected(item);
	}

    protected void onMenuItemClicked(int position, MenuItemInfo item) {
    	Log.e("omg","onMenuItemClicked");
    	
    	View v = null;
		if(mCurrentFragmentTag.equals("movieListFragment")){
			v = menuList.getChildAt(0);
		}else if(mCurrentFragmentTag.equals("folderListFragment")){
			v = menuList.getChildAt(1);
		}else if(mCurrentFragmentTag.equals("saveListFragment")){
			v = menuList.getChildAt(2);
		}else if(mCurrentFragmentTag.equals("configureFragment")){
			v = menuList.getChildAt(3);
		}
		if(v!=null)
			v.setBackgroundColor(0xff262626);
		
        if (mCurrentFragmentTag != null)
        	detachFragment(getFragment(mCurrentFragmentTag));
        attachFragment(mMenuDrawer.getContentContainer().getId(), getFragment(item.getName()), item.getName());
        mCurrentFragmentTag = item.getName();
        
        if(mCurrentFragmentTag.equals("movieListFragment")){
			v = menuList.getChildAt(0);
			getActionBar().setTitle("전체 목록");
		}else if(mCurrentFragmentTag.equals("folderListFragment")){
			v = menuList.getChildAt(1);
			getActionBar().setTitle("폴더 목록");
		}else if(mCurrentFragmentTag.equals("saveListFragment")){
			v = menuList.getChildAt(2);
			getActionBar().setTitle("저장 목록");
		}else if(mCurrentFragmentTag.equals("configureFragment")){
			v = menuList.getChildAt(3);
			getActionBar().setTitle("설정");
		}
		if(v!=null)
			v.setBackgroundResource(R.drawable.selected);
		
        mMenuDrawer.closeMenu();
    }
    
    protected int getDragMode() {
        return MenuDrawer.MENU_DRAG_WINDOW;
    }
    
    protected Position getDrawerPosition() {
        return Position.LEFT;
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current", ActivePosition);
    }

    public void onActiveViewChanged(View v) {
        mMenuDrawer.setActiveView(v, ActivePosition);
    }
    
    protected FragmentTransaction ensureTransaction() {
    	Log.e("omg","FragmentTransaction");
        if (mFragmentTransaction == null) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        }

        return mFragmentTransaction;
    }

    private Fragment getFragment(final String tag) {
    	if(tag.equals("movieListFragment")){
    		return new MovieListFragment();
    	}else if(tag.equals("saveListFragment")){
    		return new SaveListFragment();
    	}
    	else if(tag.equals("configureFragment")){
    		return new ConfigureFragment();
    	}else{
    		return new MovieListFragment();
    	}
    }

    protected void attachFragment(int layout, Fragment f, String tag) {
    	Log.e("omg","attachFragment");
    	if (f != null) {
            if (f.isDetached()) {
                ensureTransaction();
                mFragmentTransaction.attach(f);
            } else if (!f.isAdded()) {
                ensureTransaction();
                mFragmentTransaction.add(layout, f, tag);
            }
        }
    }

    protected void detachFragment(Fragment f) {
    	Log.e("omg","detachFragment");
        if (f != null && !f.isDetached()) {
            ensureTransaction();
            mFragmentTransaction.detach(f);
        }
    }

    protected void commitTransactions() {
    	Log.e("omg","commitTransactions");
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
        	mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

	
	
}
