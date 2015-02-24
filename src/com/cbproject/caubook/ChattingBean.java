package com.cbproject.caubook;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;
import io.socket.nomore.UnibookChat;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

public class ChattingBean {
	static final Logger logger = Logger.getLogger("com.cbproject.caubook.ChattingBean");
	public static ChatSocket getSocket(String fromID, String toID){
		
		ChatSocket chatSocket = new ChatSocket();
		Message msg = null;
		
		try {
			chatSocket.socket = new SocketIO(UnibookChat.UNIBOOK_CONNECTION);
			chatSocket.socket.connect(new IOCallback() {

				
				@Override
				public void on(String arg0, IOAcknowledge arg1, Object... arg2) {
					// TODO Auto-generated method stub
					logger.warning("com.cbproject.caubook.ChattingBean : on1 : " + arg0);
					logger.warning(arg2.toString());
				}

				@Override
				public void onConnect() {
					// TODO Auto-generated method stub
					logger.warning("com.cbproject.caubook.ChattingBean : onConnect!");
				}

				@Override
				public void onDisconnect() {
					// TODO Auto-generated method stub
					logger.warning("com.cbproject.caubook.ChattingBean : onDisconnect!");
				}

				@Override
				public void onError(SocketIOException arg0) {
					// TODO Auto-generated method stub
					logger.warning("com.cbproject.caubook.ChattingBean : onError1 : "+ arg0.toString());
				}

				@Override
				public void onMessage(String arg0, IOAcknowledge arg1) {
					// TODO Auto-generated method stub
					logger.warning("com.cbproject.caubook.ChattingBean : onMessage1 : "+ arg0.toString());
				}

				@Override
				public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
					// TODO Auto-generated method stub
					logger.warning("com.cbproject.caubook.ChattingBean : onMessage2 : "+ arg0.toString());
				}
	        });
			chatSocket.fromID = fromID;
			chatSocket.toID = toID;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return chatSocket;
	}

}
