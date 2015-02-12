package com.cbproject.caubook.adapter;

import android.widget.ImageView;
import android.widget.TextView;

public class SelectedCourseListViewHolder{
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
