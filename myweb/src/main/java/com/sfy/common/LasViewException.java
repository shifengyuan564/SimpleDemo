/*
 * File : LasViewException.java
 * date : Oct 10, 2012 12:31:44 AM
 */
package com.sfy.common;

/**
* [添加说明]
* <br>User: yanghongfeng
* <br>DateTime: Oct 10, 2012 12:31:44 AM
* <br>Version 1.0
*/
public class LasViewException extends RuntimeException {

	private static final long serialVersionUID = 3369843311769583686L;

	private String localizedMessage;

	private String messageStack;

	public LasViewException(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

	public LasViewException(String localizedMessage, String messageStack) {
		this.localizedMessage = localizedMessage;
		this.messageStack = messageStack;
	}

	public String getLocalizedMessage() {
		return localizedMessage;
	}

	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}

	public String getMessageStack() {
		return messageStack;
	}

	public void setMessageStack(String messageStack) {
		this.messageStack = messageStack;
	}

}
