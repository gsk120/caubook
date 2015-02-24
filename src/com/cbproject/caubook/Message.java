package com.cbproject.caubook;

public class Message {

	public Message(String msgBody) {
		this.msgBody = msgBody;
	}

	public Message(String id, String msgBody) {
		this.id = id;
		this.msgBody = msgBody;
	}

	public Message(String to, String id, String msgBody) {
		this.to = to;
		this.id = id;
		this.msgBody = msgBody;
	}

	public Message(String from, String to, String id, String msgBody) {
		this.from = from;
		this.to = to;
		this.id = id;
		this.msgBody = msgBody;
	}
	
	private String from = null;
	private String to = null;
	private String msgBody = null;
	private String id = null;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
