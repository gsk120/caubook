package com.cbproject.caubook.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;

public class BuyCourseListAdapter extends BaseAdapter{
	 
	private ArrayList<SelectedCourseListItem> buyCourseListData;
	private Context context;
	
	public BuyCourseListAdapter(Context _context, ArrayList<SelectedCourseListItem> _buyCourseListData) {
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
		holder.getTvSelectedBookPrice().setText("45000¿ø");
		holder.getImgSelectedBook().setImageResource(R.drawable.caubook);
		
		return convertView;
	}
}
