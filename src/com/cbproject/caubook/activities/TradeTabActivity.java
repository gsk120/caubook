package com.cbproject.caubook.activities;

import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.R.drawable;
import com.cbproject.caubook.R.id;
import com.cbproject.caubook.R.layout;
import com.cbproject.caubook.R.string;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

public class TradeTabActivity extends ActionBarActivity {
	
	private String[] strOptionItems = {"이형철","이현빈","이기석","이기석","이기석","이기석","이기석","조지민"};
	private ListView listOption;
	private DrawerLayout drawerLayoutOptionDrawer;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private GridView gridViewSelectedBookList;
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private SelectedCourseListAdapter selectedCourseListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_trade_tab);
		
		drawerLayoutOptionDrawer = (DrawerLayout)findViewById(R.id.drawerlayout_main_drawer);
		
		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayoutOptionDrawer, 
							R.drawable.ic_drawer, R.string.open_drawer, R.string.close_drawer){
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
			
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
			}
			
		};
		drawerLayoutOptionDrawer.setDrawerListener(actionBarDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	
		listOption = (ListView)findViewById(R.id.book_register_list);
		listOption.setAdapter(
				new ArrayAdapter<String>(
						this,android.R.layout.simple_list_item_1,strOptionItems));
		
		listOption.setOnItemClickListener(new DrawerItemclickListener());
	
		
		TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
		tabHost.setup();
		
		TabSpec tabSpecBuy = tabHost.newTabSpec("구매");
		tabSpecBuy.setIndicator("구매");
		tabSpecBuy.setContent(R.id.gridview_buy);
		tabHost.addTab(tabSpecBuy);
		
		TabSpec tabSpecSell = tabHost.newTabSpec("판매");
		tabSpecSell.setIndicator("판매");
		tabSpecSell.setContent(R.id.gridview_sell);
		tabHost.addTab(tabSpecSell);
		tabHost.setCurrentTab(1);
		/******************************************여기까진 액션바 토글관련****************************************/
		
		selectedCourseListData = (ArrayList<SelectedCourseListItem>)
									getIntent().getSerializableExtra("selectedCourseListData");

		gridViewSelectedBookList = (GridView)findViewById(R.id.gridview_sell);
		
		selectedCourseListAdapter = new SelectedCourseListAdapter(this,selectedCourseListData);	
		gridViewSelectedBookList.setAdapter(selectedCourseListAdapter);
		
		gridViewSelectedBookList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			
				Intent intent = new Intent(getApplicationContext(), AddSellingActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				intent.putExtra("position", position);
				startActivity(intent);
				finish();
			}
		});
	}//onCreate
	
	private class SelectedCourseListViewHolder{
		private TextView tvSelectedBookTitle;
		private TextView tvSelectedBookPrice;
		private ImageView imgSelectedBook;
		
		public TextView getTvSelectedBookTitle() { return tvSelectedBookTitle; }
		public TextView getTvSelectedBookPrice() { return tvSelectedBookPrice; }
		public ImageView getImgSelectedBook() { return imgSelectedBook; }
		
		public void setTvSelectedBookTitle(TextView tvSelectedBookTitle) { this.tvSelectedBookTitle = tvSelectedBookTitle; }
		public void setTvSelectedBookPrice(TextView tvSelectedBookPrice) { this.tvSelectedBookPrice = tvSelectedBookPrice; }
		public void setImgSelectedBook(ImageView imgSelectedBook) { this.imgSelectedBook = imgSelectedBook; }
	}
	
	private class SelectedCourseListAdapter extends BaseAdapter{
		
		private ArrayList<SelectedCourseListItem> selectedCourseListData;
		private Context context;
		
		private SelectedCourseListAdapter(Context _context, ArrayList<SelectedCourseListItem> _selectedCourseListData) {
			super();
			context = _context;
			selectedCourseListData = _selectedCourseListData;
			//Log.d("getCount()", selectedCourseListData.size() + "");
		}
		
		@Override
		public int getCount() {
			return selectedCourseListData.size();
		}

		@Override
		public Object getItem(int position) {
			return selectedCourseListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		public boolean isActivate(int position) {
			if(selectedCourseListData.get(position).isbBookSell()) 
				return true; 
			else 
				return false; 
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SelectedCourseListViewHolder holder;
			if(convertView == null){
				holder = new SelectedCourseListViewHolder();
				
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_selected_course_list_item, null);
				holder.setTvSelectedBookTitle((TextView)convertView.findViewById(R.id.text_selected_book_title));
				holder.setTvSelectedBookPrice((TextView)convertView.findViewById(R.id.text_selected_book_price));
				holder.setImgSelectedBook((ImageView)convertView.findViewById(R.id.img_selected_book));
				
				convertView.setTag(holder);
				
			}else{
				holder = (SelectedCourseListViewHolder) convertView.getTag();
			}
						
			
			SelectedCourseListItem data = selectedCourseListData.get(position);
			holder.getTvSelectedBookTitle().setText(data.getStrCourseTitle());
			holder.getTvSelectedBookPrice().setText("45000원");
			holder.getImgSelectedBook().setImageResource(R.drawable.caubook);
			
			//최종 등록 할 경우 활성화 시키기
			if(isActivate(position)){
				holder.getTvSelectedBookTitle().setTextColor(Color.WHITE);
				holder.getTvSelectedBookPrice().setTextColor(Color.WHITE);
				holder.getImgSelectedBook().setImageResource(R.drawable.ic_launcher);
			}else{
				holder.getTvSelectedBookTitle().setTextColor(Color.GRAY);
				holder.getTvSelectedBookPrice().setTextColor(Color.GRAY);
			}
			
			return convertView;
		}
	}
	
	private class DrawerItemclickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			switch(position){
            case 0:
            	Toast.makeText(getApplicationContext(), "drawer test", Toast.LENGTH_SHORT).show();
            	break;
            case 1:
            	break;
			}
			drawerLayoutOptionDrawer.closeDrawer(listOption);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(actionBarDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
