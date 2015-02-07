package com.cbproject.caubook;

public enum MessageTypeEnum {
	SendMsg,
	ReceiveMsg,
	None;
	
	public static int getInteger(MessageTypeEnum val) {
		switch(val) {
		case SendMsg:
			return 0;
		case ReceiveMsg:
			return 1;
		case None:
			return 2;
		default:
			return -1;
		}
	}
}
