package com.cbproject.caubook.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;

public class SelectedCourseListAdapter extends BaseAdapter{
	
	private ArrayList<SelectedCourseListItem> selectedCourseListData;
	private Context context;
	private SparseBooleanArray mSelectedItemsIds;
	
	public SelectedCourseListAdapter(Context _context, ArrayList<SelectedCourseListItem> _selectedCourseListData) {
		super();
		context = _context;
		selectedCourseListData = _selectedCourseListData;
		mSelectedItemsIds = new SparseBooleanArray();
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
	
	/***************************판매탭 삭제 관련 함수 시작****************************/
	public void remove(SelectedCourseListItem object) {
		selectedCourseListData.remove(object);
		notifyDataSetChanged();	
	}
	
	public void toggleSelection(int position) {
		selectView(position, !mSelectedItemsIds.get(position));
	}

	public void removeSelection() {
		mSelectedItemsIds = new SparseBooleanArray();
		notifyDataSetChanged();
	}

	public void selectView(int position, boolean value) {
		if (value)
			mSelectedItemsIds.put(position, value);
		else
			mSelectedItemsIds.delete(position);
		notifyDataSetChanged();
	}
	
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}

	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
	/***************************판매탭 삭제 관련 함수 끝****************************/
	
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
		holder.getImgSelectedBook().setImageResource(R.drawable.no_image);
		
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
