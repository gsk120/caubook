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
	
	int i=0; //테스트용ㄴ

	// 채팅 목록 sqlite에 저장하기 위해 DB관련 멤버변수 선언
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
	    
	    // 데이터 베이스 관련 변수 초기화
	    userTable = new UserTable(this);
	    conversationTable = new ConversationTable(this);
	    
	    if(!userTable.isExist(userID)) {
	    	// 나와 대화 상대로 상대 아이디가 있으면 새로 추가
	    	UserData user = new UserData(0, userID);
		    userTable.insertQuery(user);
	    } else {
	    	// 나와 대화ㄴ한적이 있으면 채팅 리스트 불러오기
	    	ArrayList<ConversationData> conversationList = conversationTable.selectAllQuery();
	    	for(int i=0; i<conversationList.size(); i++) {
	    		Log.i("dd", "ddddddddddd");
	    		MessageData data = new MessageData(conversationList.get(i).getContent(), conversationList.get(i).getTime());
	    		listChattingAdapter.addItem(data);
	    		listChattingAdapter.notifyDataSetChanged();
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
			ConversationData data = new ConversationData(0, i++, sendData.getMessageContent(), sendData.getMessageTime(), sendData.getMessageType());
			conversationTable.insertQuery(data);
			break;
		}
	}
	
	// 채팅 내용 데이터 클래스 내용, 송수신 시간, 송수신 메세지 타입을 저장
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
	
	// 메세지의 뷰 관리 클래스
	private class MessageVeiwHolder {
		private TextView tvContent;
		private TextView tvTime;
		
		public TextView getContentView() { return this.tvContent; }
		public TextView getTimeView() { return this.tvTime; }
		
		public void setContentView(TextView _content) { this.tvContent = _content; }
		public void setTimeView(TextView _time) { this.tvTime = _time; }
	}
	
	// 채팅리스트의 컨트롤 클래스
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

			// 뷰에 데이터를 넣어주는 부분 
			holder.getContentView().setText(data.getMessageContent());
			SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
			holder.getTimeView().setText(dateFormat.format(data.getMessageTime()));
			
			return convertView;
		}
		
		// 어답터에 데이터 채워주는 함수
		public void addItem(MessageData addItem){
			this.messageListData.add(addItem);
		}
	}
}
