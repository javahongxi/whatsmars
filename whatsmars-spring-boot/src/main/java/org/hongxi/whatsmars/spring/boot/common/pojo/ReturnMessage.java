package org.hongxi.whatsmars.spring.boot.common.pojo;

public class ReturnMessage {

	private int status = 200;
	private String message = "操作成功";

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ReturnMessage() {
		super();
	}

	public ReturnMessage(int status) {
		super();
		this.status = status;
	}

	public ReturnMessage(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public enum Message{
		
		/**
		 * 操作成功
		 */
		OPERATION_SUCCESS("操作成功"),
		/**
		 * 操作失败
		 */
		OPERATION_ERROR("操作失败"),
		/**
		 * 操作异常
		 */
		OPERATION_ABNORMAL("操作异常"),
		/**
		 * 更新异常
		 */
		UPDATE_ANOMALIES("更新异常"),
		/**
		 * 参数错误
		 */
		PARAMETER_ERROR("参数错误"),
		/**
		 * 非法请求
		 */
		ILLEGAL_REQUEST("非法请求"),
		/**
		 * 登陆超时
		 */
		LOGIN_TIMEOUT("登陆超时"),
		/**
		 * 未登录
		 */
		NOT_LOGGED_IN("未登录"),
		/**
		 * 登陆失败
		 */
		FAIL_LOGIN("登陆失败"),
		/**
		 * 存在
		 */
		EXIST("存在"),
		/**
		 * 不存在
		 */
		NOT_EXIST("不存在");
		
		String value;

		public String getValue() {
			return value;
		}

		private Message(String value) {
			this.value = value;
		}
		
	}
}
