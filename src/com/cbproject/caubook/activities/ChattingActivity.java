package com.cbproject.caubook.activities;

import io.socket.SocketIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cbproject.caubook.ChatSocket;
import com.cbproject.caubook.ChattingBean;
import com.cbproject.caubook.Message;
import com.cbproject.caubook.MessageTypeEnum;
import com.cbproject.caubook.R;
import com.cbproject.caubook.sqlite.ProductTable.ProductData;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
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

	private int oppositeID;
	private int chattingOrder;
	private ChatSocket chatSocket = null;
	
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
	    
	    ProductData productInfo = (ProductData) getIntent().getSerializableExtra("product");
	    
	    // TODO 나중에 DB에서 seller 값을 키로 판매자 아이디를 받아와야함
	    setTitle(Integer.toString(productInfo.getSeller()));
	    
	    int roomNum = getIntent().getIntExtra("roomNo", 0);
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	    String time = dataFormat.format(cal.getTime());
	    
	    new SyncMessage().execute(Integer.toString(roomNum), time);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_chatting_send:
//			String strMessage = inputMessage.getEditableText().toString();
//			if(strMessage.equals("")) {
//				break;
//			}
//			Calendar calendar = Calendar.getInstance();
//			
//			
//			new SendMessage().execute(strMessage);
//			
//			inputMessage.setText(null);
			break;
		}
	}
	
	private class SendMessage extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			//chatSocket.sendMsg(new Message(params[0]));
			return null;
		}
	}
	
	private class SyncMessage extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			return requestSyncMessage(Integer.parseInt(params[0]), params[1]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			String msg = "";
			Date date = null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			
			result = Html.fromHtml(result).toString();
			Log.e("sync_result", result);
			try {
				JSONArray jArr = new JSONArray(result);
				for (int i = 0; i < jArr.length(); i++) {
					JSONObject json = jArr.getJSONObject(i);
					
					msg = json.getString("content");
					date = dateFormat.parse(json.getString("timestamp"));
					
					MessageData sendData = new MessageData(msg, date, MessageTypeEnum.ReceiveMsg);
					listChattingAdapter.addItem(sendData);
				}
				listChattingAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		private String requestSyncMessage(int roomNo, String time) {
			URL url = null;
			String line = "";
			String lineResult = " ";
			try {
				url = new URL("http://54.92.63.117:3000/syncMsg");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				OutputStream os = conn.getOutputStream();
				time = URLEncoder.encode(time, "UTF-8");
				String outString = "roomNo=" + roomNo + "&time=" + time;
				
				os.write(outString.getBytes("UTF-8"));
				os.flush();
				os.close();
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				while((line = br.readLine()) != null){
					lineResult += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				lineResult = URLDecoder.decode(lineResult, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return lineResult;
		}
		
	}
	
	// 채팅 메세지의 데이터 클래스
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
	
	// 채팅 메세지 뷰 클래스
	private class MessageVeiwHolder {
		private TextView tvContent;
		private TextView tvTime;
		
		public TextView getContentView() { return this.tvContent; }
		public TextView getTimeView() { return this.tvTime; }
		
		public void setContentView(TextView _content) { this.tvContent = _content; }
		public void setTimeView(TextView _time) { this.tvTime = _time; }
	}
	
	// 채팅 리스트 어답터 클래스
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

			// 채팅의 전송시간 표시 
			holder.getContentView().setText(data.getMessageContent());
			SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm");
			holder.getTimeView().setText(dateFormat.format(data.getMessageTime()));
			
			return convertView;
		}
		
		// 메세지 채팅 리스트에 추가 함수
		public void addItem(MessageData addItem){
			this.messageListData.add(addItem);
		}
	}
}
