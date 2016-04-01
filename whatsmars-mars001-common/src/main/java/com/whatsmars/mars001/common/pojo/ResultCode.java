package com.whatsmars.mars001.common.pojo;

public class ResultCode {
	
	public String code;

	public String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	private ResultCode(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public static final ResultCode SUCCESS = new ResultCode("S00000", "成功");

    public static final ResultCode SYSTEM_ERROR = new ResultCode("E00001","系统错误");

    public static final ResultCode PARAMETER_ERROR = new ResultCode("E00004","请求参数错误");

    public static final ResultCode IMAGE_FORMAT_ERROR = new ResultCode("E00002","图片格式不合法");

    public static final ResultCode FILE_EXISTS_ERROR = new ResultCode("E00003","文件已经存在");

    public static final ResultCode FILE_NOT_EXISTS_ERROR = new ResultCode("E00005","文件不存在");

    public static final ResultCode VALIDATE_FAILURE = new ResultCode("E00006","验证失败");//验证失败

    public static final ResultCode NOT_LOGIN = new ResultCode("E00007","您还未登陆");

    public static final ResultCode EXPIRED = new ResultCode("E00008","表单过期，请刷新");//token过期

    public static final ResultCode PERMISSION_DENIED = new ResultCode("E00009","权限不足");

    public static final ResultCode SMS_TIMES_LIMIT = new ResultCode("E00010","短信发送次数超过限制");

	public static final ResultCode SMS_SENDING_INTERVAL_LIMIT = new ResultCode("E00011","短信发送时间间隔过短");

}
