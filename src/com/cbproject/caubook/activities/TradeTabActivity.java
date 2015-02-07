package com.cbproject.caubook.activities;

import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
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
	
	private String[] strOptionItems = {"����ö","������","�̱⼮","�̱⼮","�̱⼮","�̱⼮","�̱⼮","������"};
	private ListView listOption;
	private DrawerLayout drawerLayoutOptionDrawer;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private GridView gridViewSellBookList,gridViewBuyBookList;
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private static ArrayList<SelectedCourseListItem> buyCourseListData;//��Ƽ��Ƽ ��ȯ �� �ѹ��� �ʱ�ȭ
	private SelectedCourseListAdapter selectedCourseListAdapter;
	private BuyCourseListAdapter buyCourseListAdapter;
	
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
		
		TabSpec tabSpecBuy = tabHost.newTabSpec("����");
		tabSpecBuy.setIndicator("����");
		tabSpecBuy.setContent(R.id.gridview_buy);
		tabHost.addTab(tabSpecBuy);
		
		TabSpec tabSpecSell = tabHost.newTabSpec("�Ǹ�");
		tabSpecSell.setIndicator("�Ǹ�");
		tabSpecSell.setContent(R.id.gridview_sell);
		tabHost.addTab(tabSpecSell);
		tabHost.setCurrentTab(1);
		/******************************************������� �׼ǹ� ��۰���****************************************/
		
		selectedCourseListData = (ArrayList<SelectedCourseListItem>)
									getIntent().getSerializableExtra("selectedCourseListData");

		gridViewSellBookList = (GridView)findViewById(R.id.gridview_sell);
		
		selectedCourseListAdapter = new SelectedCourseListAdapter(this,selectedCourseListData);	
		gridViewSellBookList.setAdapter(selectedCourseListAdapter);
		
		//�Ǹ��� : �̿ϼ� �Ǹ� ��� ����Ʈ �߿��� ���� �Ǹ� ����� ���� ����
		gridViewSellBookList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			
				Intent intent = new Intent(getApplicationContext(), AddSellingActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				intent.putExtra("position", position);
				startActivity(intent);
				finish();
			}
		});
		
		//������ : ���� ���� ����� ���� ����Ʈ �����ֱ�
		gridViewBuyBookList = (GridView)findViewById(R.id.gridview_buy);
		buyCourseListData = new ArrayList<SelectedCourseListItem>();
		// ����ö ��û�ȿ���
		for(int i= 0 ; i < selectedCourseListData.size() ; i++){
			SelectedCourseListItem item = selectedCourseListData.get(i);
			
			if(item.isbBookSell()){
				buyCourseListData.add(item);
			}
		}
		
		buyCourseListAdapter = new BuyCourseListAdapter(this, buyCourseListData);
		gridViewBuyBookList.setAdapter(buyCourseListAdapter);
		
		gridViewBuyBookList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "�����׽�Ʈ", Toast.LENGTH_SHORT).show();
				
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
	
	private class BuyCourseListAdapter extends BaseAdapter{
		
		private ArrayList<SelectedCourseListItem> buyCourseListData;
		private Context context;
		
		private BuyCourseListAdapter(Context _context, ArrayList<SelectedCourseListItem> _buyCourseListData) {
			super();
			context = _context;
			buyCourseListData = _buyCourseListData;
		}
		
		@Override
		public int getCount() {
			return buyCourseListData.size();
		}

		@Override
		public Object getItem(int position) {
			return buyCourseListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
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
							
			SelectedCourseListItem data = buyCourseListData.get(position);
			
			holder.getTvSelectedBookTitle().setText(data.getStrCourseTitle());
			holder.getTvSelectedBookPrice().setText("45000��");
			holder.getImgSelectedBook().setImageResource(R.drawable.caubook);

			return convertView;
		}
	}

	private class SelectedCourseListAdapter extends BaseAdapter{
		
		private ArrayList<SelectedCourseListItem> selectedCourseListData;
		private Context context;
		
		private SelectedCourseListAdapter(Context _context, ArrayList<SelectedCourseListItem> _selectedCourseListData) {
			super();
			context = _context;
			selectedCourseListData = _selectedCourseListData;
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
			holder.getTvSelectedBookPrice().setText("45000��");
			holder.getImgSelectedBook().setImageResource(R.drawable.no_image);
			
			//���� ��� �� ��� Ȱ��ȭ ��Ű��
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
