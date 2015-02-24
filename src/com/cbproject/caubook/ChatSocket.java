package com.cbproject.caubook;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.SocketIO;

public class ChatSocket {

	public String fromID = null;
	public String toID = null;
	public SocketIO socket = null;

	public void sendMsg(Message msg) {

		JSONObject msgObject = new JSONObject();
		try {
			if (this.fromID == null) {
				msgObject.put("from", msg.getFrom());
			} else {
				msgObject.put("from", this.fromID);
			}
			if (this.toID == null) {
				msgObject.put("to", msg.getTo());
			} else {
				msgObject.put("to", this.toID);
			}
			msgObject.put("message", msg.getMsgBody());
			msgObject.put("id", msg.getId());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.socket.emit("sendMsg", msgObject);
	}

	public void syncMsg(Message msg) {

		JSONObject msgObject = new JSONObject();
		try {
			if (this.fromID == null) {
				msgObject.put("from", msg.getFrom());
			} else {
				msgObject.put("from", this.fromID);
			}
			if (this.toID == null) {
				msgObject.put("to", msg.getTo());
			} else {
				msgObject.put("to", this.toID);
			}
			msgObject.put("message", msg.getMsgBody());
			msgObject.put("id", msg.getId());
			msgObject.put("timestamp", getCurrentTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		this.socket.emit("syncMsg", msgObject);
	}
	
	public String getCurrentTime() {

		long time = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String curTime = sdf.format(new Date(time));

		return curTime;
	}

	public void disconnect() {

		socket.disconnect();
	}
}
