package com.cbproject.caubook.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.cbproject.caubook.MessageTypeEnum;
import com.cbproject.caubook.R;
import com.cbproject.caubook.sqlite.ConversationTable;
import com.cbproject.caubook.sqlite.ConversationTable.ConversationData;
import com.cbproject.caubook.sqlite.UserTable;
import com.cbproject.caubook.sqlite.UserTable.UserData;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

	// ��ȭ ���̺� �ռ�Ű�� ����
	private int oppositeID;
	private int chattingOrder;
	
	// ä�� ��� sqlite�� �����ϱ� ���� DB���� ������� ����
	private UserTable userTable;
	private ConversationTable conversationTable;
	
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
	    
	    String userID = getIntent().getStringExtra("userID");
	    setTitle(userID);
	    
	    // ������ ���̽� ���� ���� �ʱ�ȭ
	    userTable = new UserTable(this);
	    conversationTable = new ConversationTable(this);
	    
	    // ��ȭ ���̺��� �ռ�Ű�� ���� ��ȭ ����� userTable �⺻Ű �� ���� �÷� 
	    oppositeID = userTable.isExist(userID);
	    chattingOrder = 0;
	    if(oppositeID == -1) {
	    	// ���� ��ȭ ���� ��� ���̵� ������ ���� �߰�
	    	UserData user = new UserData(0, userID);
		    userTable.insertQuery(user);
	    } else {
	    	// ���� ��ȭ������ ������ ä�� ����Ʈ �ҷ�����
	    	// TODO selectAllQuery�Լ��� userID�� �ش��ϴ� �͸� �޾ƿ��� ������� ����
	    	ArrayList<ConversationData> conversationList = conversationTable.selectAllQuery(oppositeID);
	    	Log.i("ddd", Integer.toString(conversationList.size()));
	    	for(int i=0; i<conversationList.size(); i++) {
	    		MessageData data = new MessageData(conversationList.get(i).getContent(), conversationList.get(i).getTime(), conversationList.get(i).getMessageType());
	    		listChattingAdapter.addItem(data);
	    		listChattingAdapter.notifyDataSetChanged();
	    		// �ӽ÷� ���� �޾ƿ��� �ڵ�
	    		if(chattingOrder < conversationList.get(i).getOrder()) {
	    			chattingOrder = conversationList.get(i).getOrder();
	    		}
	    	}
	    }
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_chatting_send:
			String strMessage = inputMessage.getEditableText().toString();
			if(strMessage.equals("")) {
				break;
			}
			Calendar calendar = Calendar.getInstance();
			MessageData sendData = new MessageData(strMessage, calendar.getTime());
			sendData.setMessageType(MessageTypeEnum.SendMsg);
			listChattingAdapter.addItem(sendData);
			listChattingAdapter.notifyDataSetChanged();
			inputMessage.setText(null);
			ConversationData data = new ConversationData(oppositeID, ++chattingOrder, sendData.getMessageContent(), sendData.getMessageTime(), sendData.getMessageType());
			conversationTable.insertQuery(data);
			break;
		}
	}
	
	// ä�� ���� ������ Ŭ���� ����, �ۼ��� �ð�, �ۼ��� �޼��� Ÿ���� ����
	private class MessageData {
		private String msgContent;
		private Date msgTime;
		private MessageTypeEnum msgType;
		
		MessageData(String _content, Date _time) {
			this.msgContent = _content;
			this.msgTime = _time;
			this.msgType = MessageTypeEnum.None;
		}
		
		public MessageData(String _content, Date _time, MessageTypeEnum _type) {
			this.msgContent = _content;
			this.msgTime = _time;
			this.msgType = _type;
		}
		
		public String getMessageContent() { return this.msgContent; }
		public Date getMessageTime() { return this.msgTime; }
		public MessageTypeEnum getMessageType() { return this.msgType; }
		
		public void setMessageType(MessageTypeEnum _type) { this.msgType = _type; }
	}
	
	// �޼����� �� ���� Ŭ����
	private class MessageVeiwHolder {
		private TextView tvContent;
		private TextView tvTime;
		
		public TextView getContentView() { return this.tvContent; }
		public TextView getTimeView() { return this.tvTime; }
		
		public void setContentView(TextView _content) { this.tvContent = _content; }
		public void setTimeView(TextView _time) { this.tvTime = _time; }
	}
	
	// ä�ø���Ʈ�� ��Ʈ�� Ŭ����
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

			// �信 �����͸� �־��ִ� �κ� 
			holder.getContentView().setText(data.getMessageContent());
			SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
			holder.getTimeView().setText(dateFormat.format(data.getMessageTime()));
			
			return convertView;
		}
		
		// ����Ϳ� ������ ä���ִ� �Լ�
		public void addItem(MessageData addItem){
			this.messageListData.add(addItem);
		}
	}
}
