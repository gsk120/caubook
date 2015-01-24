package com.cbproject.caubook;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class TradeTabActivity extends ActionBarActivity {
	
	private String[] book_register_list_items = {"이형철","이현빈","이기석","이기석","이기석","이기석","이기석","조지민"};
	private ListView book_register_list;
	private DrawerLayout drawerlayout_main_drawer;
	private ActionBarDrawerToggle drawer_toggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_trade_tab);
		
		drawerlayout_main_drawer = (DrawerLayout)findViewById(R.id.drawerlayout_main_drawer);
		
		drawer_toggle = new ActionBarDrawerToggle(this, drawerlayout_main_drawer, 
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
		drawerlayout_main_drawer.setDrawerListener(drawer_toggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	
		book_register_list = (ListView)findViewById(R.id.book_register_list);
		book_register_list.setAdapter(
				new ArrayAdapter<String>(
						this,android.R.layout.simple_list_item_1,book_register_list_items));
		
		book_register_list.setOnItemClickListener(new DrawerItemclickListener());
		
		
		TabHost tabhost = (TabHost)findViewById(R.id.tabhost);
		tabhost.setup();
		
		TabSpec ts1 = tabhost.newTabSpec("구매");
		ts1.setIndicator("구매");
		ts1.setContent(R.id.gridlayout_main);
		tabhost.addTab(ts1);
		
		TabSpec ts2 = tabhost.newTabSpec("판매");
		ts2.setIndicator("판매");
		ts2.setContent(R.id.gridlayout_main2);
		tabhost.addTab(ts2);
		tabhost.setCurrentTab(0);
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
			drawerlayout_main_drawer.closeDrawer(book_register_list);
		}
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawer_toggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawer_toggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(drawer_toggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
