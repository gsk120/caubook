package com.cbproject.caubook.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.cbproject.caubook.R;
import com.cbproject.caubook.SelectedCourseListItem;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class AddSellingActivity extends ActionBarActivity implements
		OnClickListener {

	private static final String TEMP_PHOTO_FILE = "temp.jpg";
	private static final int REQ_CODE_PICK_IMAGE = 2;

	Button btnSelectedBookAddSelling;
	Button btnSelectBookPicture;

	String strSelectedCourseTitle;
	EditText editTextBookAddSellingCourseName;
	EditText editTextBookAddSellingPicturePath;

	ArrayList<SelectedCourseListItem> selectedCourseListData;
	int selectedPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_add_selling);
	
		btnSelectedBookAddSelling = (Button)findViewById(R.id.btn_book_add_selling);
		editTextBookAddSellingCourseName = (EditText)findViewById(R.id.edit_text_book_add_selling_course_search);
		
		btnSelectedBookAddSelling.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectedCourseListData.get(selectedPosition).setbBookSell(true);
				Intent intent = new Intent(getApplicationContext(), TradeTabActivity.class);
				intent.putExtra("selectedCourseListData", selectedCourseListData);
				startActivity(intent);
				finish();
				
				Toast.makeText(getApplicationContext(), "���� �ǿ� ��ϵǾ����ϴ�.", Toast.LENGTH_SHORT).show();
			}
		});
		
		selectedPosition = getIntent().getIntExtra("position", -1);
		selectedCourseListData = (ArrayList<SelectedCourseListItem>) getIntent()
				.getSerializableExtra("selectedCourseListData");

		editTextBookAddSellingCourseName.setText(selectedCourseListData.get(
				selectedPosition).getStrCourseTitle());

	}

	@Override
	public void onClick(View v) {

		// Intent intent = new Intent(getApplicationContext(),
		// TradeTabActivity.class);

		Intent intent = null;
		switch (v.getId()) {

		case R.id.btn_book_add_selling:
			intent = new Intent();
			selectedCourseListData.get(selectedPosition).setbBookSell(true);
			intent.putExtra("selectedCourseListData", selectedCourseListData);
			startActivity(intent);
			finish();
			break;

		case R.id.btn_book_add_selling_picture:
			intent = new Intent(intent.ACTION_PICK);
			intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
			intent.putExtra("crop", "true");
			//intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri()); // �ӽ����� ����
			intent.putExtra("outputFormat", // ���˹��
					Bitmap.CompressFormat.JPEG.toString());
			startActivityForResult(intent, REQ_CODE_PICK_IMAGE);
			break;
		}
	}

	/** �ӽ� ���� ������ ��θ� ��ȯ */
	private Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	/** ����޸𸮿� �ӽ� �̹��� ������ �����Ͽ� �� ������ ��θ� ��ȯ */
	private File getTempFile() {
		File f = new File(Environment.getExternalStorageDirectory(), // ����޸� ���
                TEMP_PHOTO_FILE);
        try {
            f.createNewFile();      // ����޸𸮿� temp.jpg ���� ����
        } catch (IOException e) {
        }

        return f;
		
	}//onCreate

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		 
        switch (requestCode) {
        case REQ_CODE_PICK_IMAGE:
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String filePath = Environment.getExternalStorageDirectory()
                            + "/temp.jpg";
 
                    System.out.println("path" + filePath); // logCat���� ���Ȯ��.
 
                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    // temp.jpg������ Bitmap���� ���ڵ��Ѵ�.
 
                    ImageView _image = (ImageView) findViewById(R.id.imageView_book_add_selling_picture);
                    _image.setImageBitmap(selectedImage); 
                    // temp.jpg������ �̹����信 �����.
                }
            }
            break;
		}
	}

}
