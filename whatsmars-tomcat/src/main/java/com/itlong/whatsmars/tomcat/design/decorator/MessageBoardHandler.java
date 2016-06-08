package com.itlong.whatsmars.tomcat.design.decorator;

/**
* @author javahongxi 用户留言板处理的接口
*/
public interface MessageBoardHandler {
    /**
	* @author javahongxi 用户可以利用函数留言
	*/
	public String filter(String msg);
}
