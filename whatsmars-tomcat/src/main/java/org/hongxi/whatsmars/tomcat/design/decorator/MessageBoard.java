package org.hongxi.whatsmars.tomcat.design.decorator;

/**
* @author javahongxi 用户留言板的具体实现
*/
public class MessageBoard implements MessageBoardHandler {

	public String filter(String msg) {
		return "留言板上的内容：" + msg;
	}
}