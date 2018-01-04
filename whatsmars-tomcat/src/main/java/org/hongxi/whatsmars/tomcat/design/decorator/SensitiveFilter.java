package org.hongxi.whatsmars.tomcat.design.decorator;

/**
* @author javahongxi 具体装饰角色，增加过滤掉政治敏感字眼的功能
*/
public class SensitiveFilter extends MessageBoardDecorator {

	public SensitiveFilter(MessageBoardHandler handler) {
	    super(handler);
	}
	public String filter(String content) {
		String temp = super.filter(content);
	    temp += "^^过滤掉政治敏感的字眼!^^";
	    return temp;
	}
}