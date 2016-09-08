package chat.common;

import java.io.Serializable;

/**
 * 发送的消息类
 * @author c
 *
 */
public class Message implements Serializable{
	private String MessageType;
	private String Content;
	private String Time;
	private String Sender;
	private String Getter;
	
	public Message(){
		
	}
	
	public Message(String MessageType,String Content,String Time,String Sender,String Getter){
		this.MessageType = MessageType;
		this.Content = Content;
		this.Time = Time;
		this.Sender = Sender;
		this.Getter = Getter;
	}

	public String getMessageType() {
		return MessageType;
	}

	public void setMessageType(String messageType) {
		MessageType = messageType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getSender() {
		return Sender;
	}

	public void setSender(String sender) {
		Sender = sender;
	}

	public String getGetter() {
		return Getter;
	}

	public void setGetter(String getter) {
		Getter = getter;
	}
	
	
}
