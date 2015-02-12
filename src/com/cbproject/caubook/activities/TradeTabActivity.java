package com.cbproject.caubook.activities;

import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;
import com.cbproject.caubook.adapter.BuyCourseListAdapter;
import com.cbproject.caubook.adapter.SelectedCourseListAdapter;
import com.cbproject.caubook.controller.AlertDialogHandler;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TabHost;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.widget.TabHost.TabSpec;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class TradeTabActivity extends ActionBarActivity {
	
	//옵션메뉴(DrawerLayout) 관련
	private String[] strOptionItems = {"로그 아웃","등록 초기화","등록 추가","환경 설정","도움말","종료","공지사항"};
	private ListView listOption;
	private DrawerLayout drawerLayoutOptionDrawer;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	
	//거래 탭 관련
	private GridView gridViewSellBookList;
	private GridView gridViewBuyBookList;
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private ArrayList<SelectedCourseListItem> buyCourseListData;
	private SelectedCourseListAdapter selectedCourseListAdapter;
	private BuyCourseListAdapter buyCourseListAdapter;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_trade_tab);
		
		selectedCourseListData = new ArrayList<SelectedCourseListItem>();
		buyCourseListData = new ArrayList<SelectedCourseListItem>();
		
		drawerLayoutOptionDrawer = (DrawerLayout)findViewById(R.id.drawerlayout_main_drawer);
		
		//옵션메뉴 토글버튼 만들기
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
		
		//옵션메뉴 만들기
		listOption = (ListView)findViewById(R.id.book_register_list);
		listOption.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strOptionItems));
		listOption.setOnItemClickListener(new DrawerItemclickListener());
	
		//구매탭, 판매탭 만들기
		makeTradeTab();
		
		//MyBookRegisterActivity에서 넘어와 체크한 과목 리스트로 받기
		selectedCourseListData = (ArrayList<SelectedCourseListItem>)
									getIntent().getSerializableExtra("selectedCourseListData");
		
		//자동 로그인시 바로 TradeTabActivity로 넘어가기 때문에 selectedCourseListData를 초기화 시켜놔도
		//getSerializableExtra 에서 null이 발생할 수 있음
		if(selectedCourseListData == null){
			selectedCourseListData = new ArrayList<SelectedCourseListItem>();
		}
		
		gridViewSellBookList = (GridView)findViewById(R.id.gridview_sell);
		selectedCourseListAdapter = new SelectedCourseListAdapter(this,selectedCourseListData);	
		gridViewSellBookList.setAdapter(selectedCourseListAdapter);

		//판매탭 목록 삭제기능
		gridViewSellBookList.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
		gridViewSellBookList.setMultiChoiceModeListener(new DeleteMultiChoiceModeListener());
		
		//판매탭 : 미완성 판매 목록 리스트 중에서 최종 판매 등록할 과목 선택
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
		
		//구매탭 : 최종 구매 등록한 과목 리스트 보여주기
		gridViewBuyBookList = (GridView)findViewById(R.id.gridview_buy);
		addBuyCourseListData();
		gridViewBuyBookList.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(), ShowBookInfoActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				intent.putExtra("buyCourseListData", buyCourseListData);
				intent.putExtra("position", position);
				startActivity(intent);
				finish();
			}
		});
	}//onCreate
	
	private class DrawerItemclickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			switch(position){
            case 0:
            	AlertDialogHandler hAlertDialogLogout = new AlertDialogHandler(TradeTabActivity.this);
            	AlertDialog alertLogout = hAlertDialogLogout.AlertDialogLogout();
            	alertLogout.show();
            	break;          	
            case 1:
            	AlertDialogHandler hAlertDialogInit = new AlertDialogHandler(TradeTabActivity.this);
            	AlertDialog alertInit = hAlertDialogInit.AlertDialogMyBookRegisterInit();
            	alertInit.show();
            	break;
            case 2:
            	AlertDialogHandler hAlertDialogModify = new AlertDialogHandler(TradeTabActivity.this);
            	AlertDialog alertModify = hAlertDialogModify.AlertDialogMyBookRegisterModify(selectedCourseListData);
            	alertModify.show();
            	break;
            case 5:
            	AlertDialogHandler hAlertDialogExit = new AlertDialogHandler(TradeTabActivity.this);
            	AlertDialog alertExit = hAlertDialogExit.AlertDialogExit();
            	alertExit.show();
            	break;
			}
			drawerLayoutOptionDrawer.closeDrawer(listOption);
		}
	}
	
	private class DeleteMultiChoiceModeListener implements MultiChoiceModeListener{

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			selectedCourseListAdapter.removeSelection();
		}
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			mode.getMenuInflater().inflate(R.menu.delete_book, menu);

			return true;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch(item.getItemId()) {
			case R.id.delete:
				SparseBooleanArray selected = selectedCourseListAdapter.getSelectedIds();
				
				for (int i = (selected.size() - 1) ; i >= 0 ; i--) {
					if (selected.valueAt(i)) {
						SelectedCourseListItem selecteditem = (SelectedCourseListItem)selectedCourseListAdapter
								.getItem(selected.keyAt(i));

						selectedCourseListAdapter.remove(selecteditem);
					}
				}
				addBuyCourseListData();	//거래 탭 업데이트
				mode.finish();
				return true;
			default:
				return false;
			}
		}
		
		@Override
		public void onItemCheckedStateChanged(ActionMode mode, int position,long id, boolean checked) {
			final int checkedCount = gridViewSellBookList.getCheckedItemCount();
			mode.setTitle(checkedCount + " Selected");
			
			selectedCourseListAdapter.toggleSelection(position);
		}
		
	}
	
	private void makeTradeTab(){
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
	}
	
	public void addBuyCourseListData(){
		buyCourseListData.clear();
		buyCourseListData = new ArrayList<SelectedCourseListItem>();
		
		for(int i= 0 ; i < selectedCourseListData.size() ; i++){
			SelectedCourseListItem item = selectedCourseListData.get(i);
			
			if(item.isbBookSell()){
				buyCourseListData.add(item);
			}
		}
		buyCourseListAdapter = new BuyCourseListAdapter(this, buyCourseListData);
		gridViewBuyBookList.setAdapter(buyCourseListAdapter);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.book_search, menu);
		
		SearchView searchView = (SearchView) menu.findItem(R.id.item_book_search).getActionView();
		searchView.setQueryHint("책 이름을 입력하세요");
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {//query로 선형대수학 입력하면
				//DB : select * from 테이블 where 책이름=query; 
				return false;
			}
			
			//책 검색기능
			@Override
			public boolean onQueryTextChange(String newText) {
				ArrayList<SelectedCourseListItem> searchCourseListData = new ArrayList<SelectedCourseListItem>();
				
				for(int i = 0 ; i < selectedCourseListData.size() ; i++){
					if(selectedCourseListData.get(i).getStrCourseTitle().contains(newText)){
						searchCourseListData.add(selectedCourseListData.get(i));
					}
				}
				selectedCourseListAdapter = new SelectedCourseListAdapter(getApplicationContext(),searchCourseListData);	
				gridViewSellBookList.setAdapter(selectedCourseListAdapter);
			
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(actionBarDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		//super.onBackPressed();
	}
}
