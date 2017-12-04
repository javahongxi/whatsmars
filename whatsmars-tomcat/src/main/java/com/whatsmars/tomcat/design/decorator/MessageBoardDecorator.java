package com.whatsmars.tomcat.design.decorator;

/**
* @author javahongxi 装饰角色
*/
public class MessageBoardDecorator implements MessageBoardHandler {

	private MessageBoardHandler handler;
	 public MessageBoardDecorator(MessageBoardHandler handler) {
	     super();
	     this.handler = handler;
	   }
	 public String filter(String msg) {
		 return handler.filter(msg);
	   }
}