package com.cbproject.caubook.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cbproject.caubook.MessageTypeEnum;
import com.cbproject.caubook.R;
import com.cbproject.caubook.R.id;
import com.cbproject.caubook.R.layout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChattingActivity extends ActionBarActivity implements OnClickListener{

	private ListView listChatting;
	private MessageListAdpater listChattingAdapter;
	private Button btnSend;
	private EditText inputMessage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.a_chatting);
	    
	    listChatting = (ListView)findViewById(R.id.list_chatting_log);
	    listChattingAdapter = new MessageListAdpater(getApplicationContext());
	    listChatting.setAdapter(listChattingAdapter);
	    
	    btnSend = (Button)findViewById(R.id.btn_chatting_send);
	    btnSend.setOnClickListener(this);
	    
	    inputMessage = (EditText)findViewById(R.id.edit_chatting_input);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_chatting_send:
			Calendar calendar = Calendar.getInstance();
			MessageData sendData = new MessageData(inputMessage.getEditableText().toString(), calendar.getTime());
			sendData.setMessageType(MessageTypeEnum.SendMsg);
			listChattingAdapter.addItem(sendData);
			listChattingAdapter.notifyDataSetChanged();
			inputMessage.setText(null);
			break;
		}
	}
	
	private class MessageData {
		private String msgContent;
		private Date msgTime;
		private MessageTypeEnum msgType;
		
		MessageData(String _content, Date _time) {
			this.msgContent = _content;
			this.msgTime = _time;
			this.msgType = MessageTypeEnum.None;
		}
		
		public String getMessageContent() { return this.msgContent; }
		public Date getMessageTime() { return this.msgTime; }
		public MessageTypeEnum getMessageType() { return this.msgType; }
		
		public void setMessageType(MessageTypeEnum _type) { this.msgType = _type; }
	}
	
	private class MessageVeiwHolder {
		private TextView tvContent;
		private TextView tvTime;
		
		public TextView getContentView() { return this.tvContent; }
		public TextView getTimeView() { return this.tvTime; }
		
		public void setContentView(TextView _content) { this.tvContent = _content; }
		public void setTimeView(TextView _time) { this.tvTime = _time; }
	}
	
	private class MessageListAdpater extends BaseAdapter {

		private Context context;
		private ArrayList<MessageData> messageListData;
		
		public MessageListAdpater(Context _context) {
			super();
			this.context = _context;
			this.messageListData = new ArrayList<MessageData>();
		}
		
		@Override
		public int getCount() {
			return messageListData.size();
		}

		@Override
		public Object getItem(int position) {
			return messageListData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MessageVeiwHolder holder;
			MessageData data = messageListData.get(position);
			
			if(convertView == null){
				holder = new MessageVeiwHolder();
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				convertView = (data.getMessageType() == MessageTypeEnum.SendMsg)
						? inflater.inflate(R.layout.list_chatting_send, null)
						: inflater.inflate(R.layout.list_chatting_receive, null);
						
				holder.setContentView((TextView)convertView.findViewById(R.id.text_chatting_msg));
				holder.setTimeView((TextView)convertView.findViewById(R.id.text_chatting_date));
				convertView.setTag(holder);	
			}else{
				holder = (MessageVeiwHolder) convertView.getTag();
			}
			
			holder.getContentView().setText(data.getMessageContent());
			SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
			holder.getTimeView().setText(dateFormat.format(data.getMessageTime()));
			
			return convertView;
		}
		
		public void addItem(MessageData addItem){
			this.messageListData.add(addItem);
		}
	}
}
